package com.example.spellbook5eapplication.app.Model.Data_Model

import com.google.gson.annotations.SerializedName

class Spell{
data class SpellInfo(

    var json: String?,

    @SerializedName("index")
    var index: String?,

    @SerializedName("name")
    var name: String?,

    @SerializedName("desc")
    var desc: List<String>?,

    @SerializedName("higher_level")
    var atHigherLevel: List<String>?,

    @SerializedName("range")
    var range: String?,

    @SerializedName("components")
    var components: List<String>?,

    @SerializedName("material")
    var materials: String?,

    @SerializedName("ritual")
    var ritual: Boolean?,

    @SerializedName("duration")
    var duration: String?,

    @SerializedName("concentration")
    var concentration: Boolean?,

    @SerializedName("casting_time")
    var casting_time: String?,

    @SerializedName("level")
    var level: Int?,

    @SerializedName("school")
    var school: String?,

    @SerializedName("classes")
    var classes: List<String>?,

    @SerializedName("url")
    var url: String?,

    @SerializedName("attack_type")
    var attackType: String?,

    @SerializedName("damage")
    var damage: String?,

    @SerializedName("dc")
    var dc: String?,

    @SerializedName("area_of_effect")
    var aoe: String?,

    var homebrew: Boolean?)

    data class SpellOverview(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("url") val url: String?
    )

    data class SpellsResponseOverview(
        @SerializedName("count") val count: Int,
        @SerializedName("results") val spells: List<Spell.SpellOverview>
    )
}