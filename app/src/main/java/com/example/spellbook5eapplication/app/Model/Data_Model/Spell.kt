package com.example.spellbook5eapplication.app.Model.Data_Model

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.spellbook5eapplication.app.Utility.Displayable
import com.example.spellbook5eapplication.app.view.spellCards.SpellCard
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
    var school: SpellSchool?,

    @SerializedName("classes")
    var classes: List<SpellClasses>?,

    @SerializedName("url")
    var url: String?,

    @SerializedName("attack_type")
    var attackType: String?,

    @SerializedName("damage")
    var damage: SpellDamage?,

    @SerializedName("dc")
    var dc: String?,

    var homebrew: Boolean?): Displayable {
    override fun renderCardComposable(spell: Displayable): @Composable () -> Unit {
    return {

    if (spell is SpellInfo) {
    //Render SpellCard if it's a Spell
        SpellCard(spell = spell)
    } else {

    Log.d("Errors","Error: Item is not a Spell")
                }
            }
        }
    }

    data class SpellNames(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
        val url: String?
    )

    data class SpellsResponseOverview(
        @SerializedName("count") val count: Int,
        @SerializedName("results") val spells: List<SpellNames>
    )

    data class SpellDamage(
        @SerializedName("damage_type") val damageType: SpellDamageType?,
    )

    data class SpellDamageType(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
    )

    data class SpellSchool(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
    )

    data class SpellClasses(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
    )

    data class SpellClass(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?
    )



}