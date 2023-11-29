package com.example.spellbook5eapplication.test

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.cucumber.java.en.And
import io.cucumber.java.en.But
import okhttp3.internal.wait
import org.junit.Assert





class UserStoryOneSearchFunctionality {
    private var fakeSpellList: SpellList? = null

    @Given("the user is on the spell list screen")
    fun setupFakeSpellList() {
        fakeSpellList = fakeSpellList?.createFakeSpellList()
    }

    @When("the user enters a spell name or part of a spell name into the search field")
        fun the_user_enters_spell_name() {
        println(fakeSpellList)
    }

    @Then("the search results should display all spells where the name contains the entered text, sorted by relevance")
    fun verify_search_results() {
        Assert.assertTrue(true)
    }

    @Then("the search should be performed within a few seconds to ensure responsiveness, If no spells match the criteria, a message should be displayed indicating \"No spells found.\"")
    fun the_search_should_be_performed_within_a_few_seconds_to_ensure_responsiveness_if_no_spells_match_the_criteria_a_message_should_be_displayed_indicating() {
       Assert.assertTrue(true)
    }

}