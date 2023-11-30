package com.example.spellbook5eapplication.test

import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.SpellController
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.cucumber.java.en.And
import io.cucumber.java.en.But
import org.junit.Assert
import java.util.Locale


class UserStoryOneSearchFunctionality {
    private var fakeSpellList: SpellList? = null

    @Given("the user is on the spell list screen")
    fun setupFakeSpellList() {
        fakeSpellList = SpellList().createFakeSpellList()
    }

    @When("the user enters a spell name or part of a spell name into the search field")
    fun the_user_enters_spell_name() {
        val filter = Filter().apply { setSpellName("fire") }

        if (fakeSpellList != null && fakeSpellList!!.getIndexList().isNotEmpty()) {
            fakeSpellList = SpellController.searchSpellListWithFilter(fakeSpellList!!, filter)
        } else {
            println("Spell list is empty or not initialized.")
        }
    }

    @Then("the search results should display all spells where the name contains the entered text, sorted by relevance")
    fun verify_search_results() {
        val fakeSpellInfoList = fakeSpellList?.getSpellInfoList()
        var filteredSearch = true;
        for (spellinfo in fakeSpellInfoList!!) {
            if(!spellinfo.name?.lowercase(Locale.getDefault())?.contains("fire")!!){
                filteredSearch = false
            }
        }
        Assert.assertTrue(filteredSearch)
    }

}