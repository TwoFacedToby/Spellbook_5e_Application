package com.example.spellbook5eapplication.test

import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.Search
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert

class UserStoryTwoFilterFunctionality {
    private var fakeSpellList = SpellList()
    private var spellinfo1: Spell.SpellInfo = (Spell.SpellInfo(
        json = "",
        index = "fireball",
        name = "Fireball",
        desc = listOf("Fireball fires fireballs."),
        atHigherLevel = listOf(),
        range = "",
        components = listOf(),
        materials = "",
        ritual = false,
        duration = "",
        concentration = false,
        casting_time = "",
        level = 0,
        school = Spell.SpellSchool(index = "", name = "", url = "Homebrew"),
        classes = listOf(),
        url = "",
        attackType = "",
        damage = Spell.SpellDamage(Spell.SpellDamageType(name = "", index = "")),
        dc = "",
        homebrew = true
    ))
    private var spellinfo2: Spell.SpellInfo = (Spell.SpellInfo(
        json = "",
        index = "fire-shield",
        name = "Fire shield",
        desc = listOf("Fire shield protects fire shields."),
        atHigherLevel = listOf(),
        range = "",
        components = listOf(),
        materials = "",
        ritual = false,
        duration = "",
        concentration = false,
        casting_time = "",
        level = 0,
        school = Spell.SpellSchool(index = "", name = "", url = "Homebrew"),
        classes = listOf(),
        url = "",
        attackType = "",
        damage = Spell.SpellDamage(Spell.SpellDamageType(name = "", index = "")),
        dc = "",
        homebrew = true
    ))
    private var spellinfo3: Spell.SpellInfo = (Spell.SpellInfo(
        json = "",
        index = "water-bolt",
        name = "Water Bolt",
        desc = listOf("Water bolt waters water bolts."),
        atHigherLevel = listOf(),
        range = "",
        components = listOf(),
        materials = "",
        ritual = false,
        duration = "",
        concentration = false,
        casting_time = "",
        level = 0,
        school = Spell.SpellSchool(index = "", name = "", url = "Homebrew"),
        classes = listOf(),
        url = "",
        attackType = "",
        damage = Spell.SpellDamage(Spell.SpellDamageType(name = "", index = "")),
        dc = "",
        homebrew = true
    ))


    init {
        // Add spells to the fakeSpellList
        val spellInfos = listOf(spellinfo1, spellinfo2, spellinfo3)
        fakeSpellList.setSpellInfoList(listOf(spellinfo1, spellinfo2, spellinfo3))

        fakeSpellList.setIndexList(spellInfos.mapNotNull { it.index })
    }

    @Given("the user is viewing a list of spells")
    fun the_user_is_viewing_a_list_of_spells() {
        // The fakeSpellList is already initialized with spells
    }

    @When("the user applies a filter based on specific attributes \\(e.g., level, school, class)")
    fun the_user_applies_a_filter_based_on_specific_attributes_e_g_level_school_class( ) {
        val filter = Filter()
        filter.apply { addLevel(2) }

        if (fakeSpellList != null && fakeSpellList!!.getIndexList().isNotEmpty()) {
            val search = Search()
            fakeSpellList = search.searchSpellListWithFilter(fakeSpellList!!, filter)
        } else {
            println("Spell list is empty or not initialized.")
        }
    }


    @Then("the list should update to show only spells that match the selected attributes")
    fun the_list_should_update_to_show_only_spells_that_match_the_selected_attributes( ) {
        val fakeSpellInfoList = fakeSpellList?.getSpellInfoList()
        var filteredSearch = true;
        for (spellinfo in fakeSpellInfoList!!) {
            if(spellinfo.level?.equals(2) != true){
                filteredSearch = false
            }
        }
        Assert.assertTrue(filteredSearch)
    }
}