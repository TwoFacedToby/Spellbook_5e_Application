package com.example.spellbook5eapplication.app.Model
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RetroFitAPI {
    @GET("spells")
    suspend fun getSpells(): Response<Spell.SpellsResponseOverview>

    @GET("spells/{index}")
    suspend fun getSpellInfo(): Response<Spell.SpellInfo>
}