package com.dtu.uemad.birthdaycardtest.Model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.net.UnknownHostException

class API {
    var percentage = 0 //A public variable that could be used to get how far the app has loaded, it's an integer between 0 and 100
    private var spellsDone = 0 //Amount of spells loaded in current search, is used to calculate percentage
    private var total = 1 //Total of spells asked to be loaded in current search, is used to calculate percentage
    private val client = OkHttpClient() //Our Client

    /**@author Tobias s224271
     * @param spellName The name of the spell that should searched for in the api
     * @return Null value if problem occurred in connection, otherwise the json value of the spell in the form of a String
     *
     * Cannot be called in main thread so is called in a Coroutine Scope, returns with context.
     */
    suspend fun getSpellFromApi(spellName: String): String? {
        return withContext(Dispatchers.IO) {
            val spellInfo = getDnD5eSpell(spellName)
            spellInfo
        }
    }
    /**@author Tobias s224271
     * @param spellName The name of the spell that should be loaded from the api
     * @param maxRetries The amount of times the program can fail to load the spell before it should give up
     * @return Null value if problem occurred in connection, otherwise the json value of the spell in the form of a String
     *
     * calls the getSpellFromApi within a while loop as long as the amount of times it has tried is lower than the maxRetries.
     * If it finds the spell from the api it breaks the while loop
     * It then continues to calculate the percentage of spells we are currently at from current query
     * Then returns the spell.
     * There is a delay between each try, just to give the api a break, as it seems to not like a lot of calls really quickly.
     */
    suspend fun getSpellFromApiWithRetry(spellName: String, maxRetries: Int): String? {
        var retryCount = 0
        var result: String? = null

        while (retryCount < maxRetries) {
            try {
                result = getSpellFromApi(spellName)
                if (result != null) {
                    break
                }
            } catch (e: UnknownHostException) {
                // Handle the exception or log it
            }

            // Wait for a short duration before retrying
            delay(1000) // 1000 = 1 second delay

            retryCount++
        }
        percentage = ((spellsDone++*1.0)/total*100).toInt()
        println("${percentage}%")
        return result
    }
    /**@author Tobias s224271
     * @return A null value of no connection or other problem, otherwise it returns the names of all spells in the api in the form of a json in a String
     *
     * calls the getJsonStringFromCommand method with the specific command that is needed to get all spells and returns with conext.
     *
     */
    suspend fun getListOfSpells() : String?{
        return withContext(Dispatchers.IO) {
            val command = "https://www.dnd5eapi.co/api/spells"
            getJsonStringFromCommand(command)
        }
    }
    /**@author Tobias s224271
     * @param spellName The name of the spell we want from the api
     * @return A null value if problem occurred, otherwise a json with the information from a spell, in the form of a String
     *
     * Calls the command needed to get a spell from the api with the specific spell name, using the getJsonStringFromCommand function
     */
    private fun getDnD5eSpell(spellName: String): String? {
        val command = "https://www.dnd5eapi.co/api/spells/$spellName"
        return getJsonStringFromCommand(command)
    }
    /**@author Tobias s224271
     * @param spellNames A list of Strings with the spell names you would want to get additional information for
     * @return A list of Strings each a json with information of each spell. Can be null if problem in connection
     *
     * resets the spellsDone and percentage variables while setting the total of spells to the length of the list.
     * it then starts a coroutine scope to get every spell from the spell list, creating a list to contain them and waiting for all to be returned
     * it then returns the list of all the spell information in a List of jsons in the form of Strings
     *
     */
    suspend fun getSpellsFromApi(spellNames: List<String>): List<String?> {
        total = spellNames.size
        spellsDone = 0
        percentage = 0
        return coroutineScope {
            spellNames.map { spellName ->
                async {
                    getSpellFromApiWithRetry(spellName, 100)
                }
            }.awaitAll()
        }
    }
    /**@author Tobias s224271
     * @param command The command that will be sent to the api
     * @return A json that holds the response of the api in the form of a String, can be null if there is a problem with the connection.
     *
     * The function creates a request based on your command that expects a json as response
     * It then tries to get a response from the client
     */
    private fun getJsonStringFromCommand(command : String) : String? {
        val request = Request.Builder()
            .url(command)
            .header("Accept", "application/json")
            .build()

        try {
            // Execute the request and get the response
            val response: Response = client.newCall(request).execute()
            // Check if the request was successful (HTTP status code 200)
            if (response.isSuccessful) {
                return response.body?.string()
            } else {
                println("Error: ${response.code} - ${response.message}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


}