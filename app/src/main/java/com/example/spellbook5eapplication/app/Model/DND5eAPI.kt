package com.example.spellbook5eapplication.app.Model

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
class DND5eAPI {
    private val client = OkHttpClient() // Our Client

    fun getDnD5eSpell(index: String): String? {
        val graphqlQuery = """
        query SpellQuery($index: String) {

  spell(index: "fireball") {
                name
                casting_time
                classes {
                  name
                }
                components
                concentration
                desc
                duration
                higher_level
                level
                name
                material
                range
                ritual
                school {
                  name
                }
              }         
            
}  
        """.trimIndent()

        val jsonMediaType = "application/json; charset=utf-8".toMediaType()
        val body = graphqlQuery.toRequestBody(jsonMediaType)

        val request = Request.Builder()
            .url("https://www.dnd5eapi.co/graphql")
            .post(body)
            .build()

        return getJsonStringFromRequest(request)
    }

    fun getJsonStringFromRequest(request: Request): String? {
        try {
            val response: Response = client.newCall(request).execute()
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