package com.example.spellbook5eapplication.test

import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.Search
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert
import java.util.Locale


class UserStoryOneSearchFunctionality {
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



    @Given("the user is on the spell list screen")
    fun setupFakeSpellList() {
        val spellInfos = listOf(spellinfo1, spellinfo2, spellinfo3)
        fakeSpellList.setSpellInfoList(listOf(spellinfo1, spellinfo2, spellinfo3))

        fakeSpellList.setIndexList(spellInfos.mapNotNull { it.index })
    }

    @When("the user enters a spell name or part of a spell name into the search field")
    fun the_user_enters_spell_name() {
        val filter = Filter().apply { setSpellName("fire") }

        if (fakeSpellList != null && fakeSpellList.getIndexList().isNotEmpty() && fakeSpellList.getSpellInfoList().isNotEmpty()){
            val search = Search()
            fakeSpellList = search.searchSpellListWithFilter(fakeSpellList, filter)
        } else {
            println("Spell list is empty or not initialized.")
        }
    }

    @Then("the search results should display all spells where the name contains the entered text, sorted by relevance")
    fun verify_search_results() {
        val fakeSpellInfoList = fakeSpellList.getSpellInfoList()
        var filteredSearch = true
        for (spellinfo in fakeSpellInfoList!!) {
            if(!spellinfo.name?.lowercase(Locale.getDefault())?.contains("fire")!!){
                filteredSearch = false
            }
        }
        Assert.assertTrue(filteredSearch)
    }

}