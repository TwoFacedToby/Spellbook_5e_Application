package com.example.spellbook5eapplication.test

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader

import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert
class UserStoryThreeSelectSpecificSpellbook {
    private var selectedSpellbook = mutableStateOf<Spellbook?>(null)
    private val spellbooks = mutableStateListOf<Spellbook>()
    private var selectedSpellbookTemp: Spellbook? = null

    init {
        prepareSpellbooksForTesting()
    }

    private fun prepareSpellbooksForTesting() {
        // Clear existing spellbooks
        SpellbookManager.getAllSpellbooks().forEach {
            SpellbookManager.removeSpellbook(it.spellbookName)
        }

        // Create and add new spellbooks with unique spells
        val spellbook1 = Spellbook("Spellbook1").apply {
            addSpellToSpellbook("Fireball")
        }
        val spellbook2 = Spellbook("Spellbook2").apply {
            addSpellToSpellbook("Ice Lance")
        }
        val spellbook3 = Spellbook("Spellbook3").apply {
            addSpellToSpellbook("Earthquake")
        }

        SpellbookManager.addSpellbook(spellbook1)
        SpellbookManager.addSpellbook(spellbook2)
        SpellbookManager.addSpellbook(spellbook3)
    }

    @Given("a list of user-created spellbooks")
    fun a_list_of_user_created_spellbooks() {
        spellbooks.clear()
        spellbooks.addAll(SpellbookManager.getAllSpellbooks())
        selectedSpellbook.value = spellbooks.firstOrNull()
    }

    @When("the user selects a specific spellbook")
    fun the_user_selects_a_specific_spellbook() {
        selectedSpellbookTemp = selectedSpellbook.value

        if(spellbooks.size > 1) {
            selectedSpellbook.value = spellbooks.getOrNull(1)
        }
    }

    @Then("the content of that spellbook should be displayed")
    fun the_content_of_that_spellbook_should_be_displayed( ) {
        var spellbookChanged = false
        //Checking if the spells arrayList object is the same, if not, a new spellbook have been chosen.
        if(selectedSpellbookTemp?.spells != selectedSpellbook.value?.spells){
            spellbookChanged = true
        }
        Assert.assertTrue(spellbookChanged)
    }
}