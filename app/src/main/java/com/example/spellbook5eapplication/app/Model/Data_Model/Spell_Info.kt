package com.dtu.uemad.birthdaycardtest.Model.Data_Model
import com.google.gson.annotations.SerializedName
class Spell_Info {




    data class SpellInfo(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("desc") val description: List<String>?,
        @SerializedName("higher_level") val higherLevelDescription: List<String>?,
        @SerializedName("range") val range: String?,
        @SerializedName("components") val components: List<String>?,
        @SerializedName("material") val material: String?,
        @SerializedName("ritual") val isRitual: Boolean?,
        @SerializedName("duration") val duration: String?,
        @SerializedName("concentration") val isConcentration: Boolean?,
        @SerializedName("casting_time") val castingTime: String?,
        @SerializedName("level") val level: Int?,
        @SerializedName("school") val school: SpellSchool?,
        @SerializedName("classes") val classes: List<SpellClass>?,
        @SerializedName("subclasses") val subclasses: List<SpellClass>?,
        @SerializedName("url") val url: String?,
        @SerializedName("attack_type") val attackType: String?,
        @SerializedName("damage") val damage: SpellDamage?,
        @SerializedName("dc") val dc: SpellDC?,
        @SerializedName("area_of_effect") val areaOfEffect: SpellAreaOfEffect?,
        @SerializedName("heal_at_slot_level") val healAtSlotLevel: SpellHealAtSlotLevel?,
        @SerializedName("higher_level_ability") val higherLevelAbility: List<String>?,
        @SerializedName("archetype") val archetype: String?,
        @SerializedName("race") val race: String?,
        @SerializedName("time_of_day") val timeOfDay: String?,
        @SerializedName("circle") val circle: String?,
        @SerializedName("domain") val domain: String?,
        @SerializedName("eldritch_invocations") val eldritchInvocations: String?,
        @SerializedName("patron") val patron: String?,
        @SerializedName("oaths") val oaths: String?,
        @SerializedName("sorcerous_origins") val sorcerousOrigins: String?,
        @SerializedName("otherworldly_patrons") val otherworldlyPatrons: String?
    )

    data class SpellSchool(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("url") val url: String?
    )

    data class SpellClass(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("url") val url: String?
    )

    data class SpellDamage(
        @SerializedName("damage_type") val damageType: DamageType?,
        @SerializedName("damage_at_slot_level") val damageAtSlotLevel: SpellDamageAtSlotLevel?
    )

    data class DamageType(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?
    )

    data class SpellDamageAtSlotLevel(
        @SerializedName("level") val level: Int?,
        @SerializedName("damage") val damage: List<String>?
    )

    data class SpellDC(
        @SerializedName("dc_type") val dcType: SpellDCType?,
        @SerializedName("dc_success") val dcSuccess: String?,
        @SerializedName("dc_failure") val dcFailure: String?
    )

    data class SpellDCType(
        @SerializedName("index") val index: String?,
        @SerializedName("name") val name: String?
    )

    data class SpellAreaOfEffect(
        @SerializedName("type") val type: String?,
        @SerializedName("size") val size: String?
    )

    data class SpellHealAtSlotLevel(
        @SerializedName("level") val level: Int?,
        @SerializedName("healing") val healing: List<String>?
    )

}