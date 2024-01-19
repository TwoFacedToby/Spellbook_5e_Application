package com.example.spellbook5eapplication.app.Utility

import android.util.Log
//import io.github.nefilim.kjwt.JWT
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Repository.HomeBrewRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object JsonTokenManager {
    private val repository: HomeBrewRepository = HomeBrewRepository()

    fun tokenise(json: String, index: String): String {
        val algorithm = Algorithm.none()

        val token = JWT.create()
            .withClaim("index", index)
            .withClaim("json", json)
            .sign(algorithm)

        return token
    }


    fun tokenMyHomebrew(index: String): String{
        val json = LocalDataLoader.getJson(index, LocalDataLoader.DataType.HOMEBREW)
        try {
            val token = tokenise(json!!, index)
            return token
        } catch (e: Exception) {
            println("Error in tokenMyHomebrew")
            return ""
        }
    }

    fun saveTokenAsHomebrew(token: String) {
        val parts = token.split(".")
        if (parts.size != 3) {
            return
        }

        val decodedToken = JWT.decode(token)
        val jsonClaim = decodedToken.getClaim("json")
        val json = jsonClaim.asString() ?: ""

        if (json.isEmpty()) {
            return
        }

        try {
            val graphQLResponse = Gson().fromJson(json, Spell.GraphQLResponse::class.java)

            // Check if the JSON structure contains the necessary fields
            if (graphQLResponse.data?.spell?.index != null) {
                // If the index exists, we can proceed to save the JSON
                LocalDataLoader.saveJson(
                    json,
                    graphQLResponse.data.spell.index!!,
                    LocalDataLoader.DataType.HOMEBREW
                )
                saveSpellToFirebase(graphQLResponse.data.spell.index!!, json)

            } else {
                Log.d("TOKEN1", "Token does not have the required structure.")
            }
        } catch (e: JsonSyntaxException) {
            Log.d("TOKEN1", "Invalid JSON format.", e)
        }
    }

    fun saveSpellToFirebase(index: String, spell: String){
        repository.saveHomeBrewSpell(GlobalLogInState.userId, index, spell)
    }

    fun loadAllSpellsFromFirebase(onDataReceived: (Map<String, String?>) -> Unit) {
        repository.loadAllHomeBrewSpellsFromFirebase(GlobalLogInState.userId, onDataReceived)
    }
}