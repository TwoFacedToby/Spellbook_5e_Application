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
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.math.pow

class SpellsViewModel(private val context: Context) : ViewModel() {
    private val _spells = MutableLiveData<List<Spell.SpellNames>>()
    val spells: LiveData<List<Spell.SpellNames>> = _spells
    private val BASE_URL_GRAPHQL = "https://www.dnd5eapi.co/graphql/"
    private val BASE_URL_API = "https://www.dnd5eapi.co/api/"
    private val TAG: String = "API_RESPONSE_NEW"
    private val _spellsOverview = MutableLiveData<List<Spell.SpellNames>>()
    val spellsOverview: LiveData<List<Spell.SpellNames>> = _spellsOverview
    private val _spellDetails = MutableLiveData<List<Spell.SpellInfo>>()
    val spellDetails: LiveData<List<Spell.SpellInfo>> = _spellDetails
    private val _isLoading = MutableLiveData<Boolean>()
    private val failedIndices = mutableListOf<String>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        // Initially, no loading.
        _isLoading.value = false
    }

    private fun loadGraphQLQueryFromFile(): String {
        return try {
            context.assets.open("spell_query.graphql").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            Log.e(TAG, "Error reading GraphQL query from file", ioException)
            ""
        }
    }

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


    fun fetchAllSpellNames() {
        _isLoading.postValue(true) //Start loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = api.getSpells()
                if (response.isSuccessful) {
                    response.body()?.let { spellsResponse ->
                        _spellsOverview.postValue(spellsResponse.spells)
                        Log.e(TAG, "Spells: ${spellsResponse.spells}")
                    }
                } else {
                    Log.e(
                        TAG,
                        "Failed to retrieve spell names. HTTP Status Code: ${response.code()}"
                    )
                    Log.e(TAG, "Error Body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            } finally {
                _isLoading.postValue(false) //End loading }
            }
        }
    }

    suspend fun getSpellDetails(indice: String): String {
        val graphQLQuery = loadGraphQLQueryFromFile()
        val variablesMap = mapOf("index" to indice)
        val requestBody = GraphQLRequestBody(query = graphQLQuery, variables = variablesMap)

        return try {
            val response = graphql.performGraphQLQuery(requestBody)
            if (response.isSuccessful) {
                // Return the raw JSON string
                response.body()?.toString() ?: ""
            } else {
                Log.e(TAG, "Failed to fetch details for index $indice: ${response.errorBody()?.string()}")
                ""
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching details for index $indice", e)
            ""
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
        val graphQLQuery = loadGraphQLQueryFromFile()
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