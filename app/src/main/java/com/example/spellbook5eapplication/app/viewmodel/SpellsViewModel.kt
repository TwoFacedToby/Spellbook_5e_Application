package com.example.spellbook5eapplication.app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.RetroFitAPI
import com.example.spellbook5eapplication.app.Utility.GraphQLRequestBody
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.JSON_to_Spell
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.math.pow
import kotlin.math.min

object SpellsViewModel : ViewModel() {
    private val BASE_URL_GRAPHQL = "https://www.dnd5eapi.co/graphql/"
    private val BASE_URL_API = "https://www.dnd5eapi.co/api/"
    private val TAG: String = "API_RESPONSE_NEW"
    private val jsonToSpell = JSON_to_Spell()
    private val gson = Gson()
    private val failedIndices = mutableListOf<String>()

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetroFitAPI::class.java)

    private val graphql = Retrofit.Builder()
        .baseUrl(BASE_URL_GRAPHQL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetroFitAPI::class.java)


    suspend fun fetchAllSpellNames(): SpellList {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getSpells()
                Log.d(TAG, "MKL: " + response.body().toString())
                if (response.isSuccessful) {
                    response.body()?.let { spellsResponse ->
                         Log.d(TAG, jsonToSpell.jsonToSpellList(spellsResponse).toString())
                         jsonToSpell.jsonToSpellList(spellsResponse)
                    } ?: SpellList() // Return empty list if body is null
                } else {
                    Log.e(TAG, "Failed to retrieve spell names. HTTP Status Code: ${response.code()}")
                    Log.e(TAG, "Error Body: ${response.errorBody()?.string()}")
                    SpellList() // Return empty list on failure
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
                SpellList() // Return empty list on exception
            } finally {
                Log.d(TAG, "We did it my baby")
            }
        }
    }


    suspend fun getSpellDetails(index: String): String {
        val graphQLQuery = LocalDataLoader.loadGraphQLQueryFromFile()
        val variablesMap = mapOf("index" to index)
        val requestBody = GraphQLRequestBody(query = graphQLQuery, variables = variablesMap)

        return try {
            val response = graphql.performGraphQLQuery(requestBody)
            if (response.isSuccessful) {
                // Return the raw JSON string
                response.body()?.toString() ?: ""
            } else {
                Log.e(TAG, "Failed to fetch details for index $index: ${response.errorBody()?.string()}")
                failedIndices.add(index)
                ""
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching details for index $index", e)
            ""
        }
    }

    suspend fun fetchNextSpellDetails(spellList: SpellList, pageSize: Int = 10): List<Spell.SpellInfo> {
        return withContext(Dispatchers.IO) {
            val deferredResults = mutableListOf<Deferred<Spell.SpellInfo?>>()
            val indices = spellList.getIndexList()
            val startIndex = spellList.getLoaded()

            // Adjust the range for fetching based on loaded count and page size
            val endIndex = min(startIndex + pageSize, indices.size)

            for (i in startIndex until endIndex) {
                val deferred = async {
                    try {
                        val jsonResult = getSpellDetails(indices[i])
                        if (jsonResult.isNotEmpty()) {
                            val jsonObject = gson.fromJson(jsonResult, JsonObject::class.java)
                            val spellJson = jsonObject.getAsJsonObject("data")?.getAsJsonObject("spell")
                            gson.fromJson(spellJson, Spell.SpellInfo::class.java)
                        } else {
                            failedIndices.add(indices[i])
                            null
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error fetching details for index ${indices[i]}", e)
                        failedIndices.add(indices[i])
                        null
                    }
                }
                deferredResults.add(deferred)
            }

            // Await results and filter out nulls
            val results = deferredResults.awaitAll().filterNotNull()

            // Update the loaded count in SpellList
            spellList.setLoaded(startIndex + results.size)

            results
        }
    }

    /**
     * Retry fetching details for the spells that failed in the first attempt, without slowing the actual responses down.
     * @param failedIndices List of indices for which the first attempt failed
     * @param graphQLQuery The GraphQL query to fetch spell details
     * @param gson Gson object to parse JSON
     * @return List of SpellInfo objects
     *
     */
    suspend fun retryFailedSpellDetails(index: String, retryAttempt: Int = 0): String {
        val graphQLQuery = LocalDataLoader.loadGraphQLQueryFromFile()
        val maxRetries = 3
        val baseDelay = 700L  // Start with 0.7 seconds delay.
        val maxDelay = 6000L  // Maximum delay = 6 seconds
        val delayTime = (baseDelay * (2.0.pow(retryAttempt.toDouble()))).toLong().coerceAtMost(maxDelay)  // Exponential backoff

        // Delay before the next retry attempt.
        delay(delayTime)

        val variablesMap = mapOf("index" to index)
        val requestBody = GraphQLRequestBody(query = graphQLQuery, variables = variablesMap)

        return try {
            val response = graphql.performGraphQLQuery(requestBody)
            if (response.isSuccessful) {
                // Return the raw JSON string
                response.body()?.toString() ?: ""
            } else {
                Log.e(TAG, "Failed to fetch details for index $index: ${response.errorBody()?.string()}")
                if (retryAttempt < maxRetries) {
                    // Retry if the number of attempts is less than maxRetries
                    retryFailedSpellDetails(index, retryAttempt + 1)
                } else {
                    ""
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching details for index $index", e)
            if (retryAttempt < maxRetries) {
                // Retry if the number of attempts is less than maxRetries
                retryFailedSpellDetails(index, retryAttempt + 1)
            } else {
                ""
            }
        }
    }
}