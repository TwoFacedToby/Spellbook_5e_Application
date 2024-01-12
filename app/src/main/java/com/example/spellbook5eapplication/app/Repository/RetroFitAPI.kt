package com.example.spellbook5eapplication.app.Repository
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Utility.GraphQLRequestBody
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroFitAPI {
    @GET("spells")
    suspend fun getSpells(): Response<Spell.SpellsResponseOverview>

    @GET("spells/{index}")
    suspend fun getSpellInfo(@Path("index") index: String): Response<Spell.SpellInfo>

    @Headers("Content-Type: application/json")
    @POST("graphql")
    suspend fun performGraphQLQuery(@Body requestBody: GraphQLRequestBody): Response<JsonObject>

}