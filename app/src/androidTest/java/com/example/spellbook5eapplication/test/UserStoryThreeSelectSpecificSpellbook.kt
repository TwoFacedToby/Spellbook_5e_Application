package com.example.spellbook5eapplication.test

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

    fun loadSpellbooks(){
        SpelllistLoader.loadSpellbooks()
        spellbooks.clear()
        spellbooks.addAll(SpellbookManager.getAllSpellbooks())
        selectedSpellbook.value = spellbooks.firstOrNull()
    }


    @Given("a list of user-created spellbooks")
    fun a_list_of_user_created_spellbooks() {
        loadSpellbooks()
    }

    @When("the user selects a specific spellbook")
    fun the_user_selects_a_specific_spellbook() {
        loadSpellbooks()
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