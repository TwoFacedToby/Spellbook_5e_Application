package com.example.spellbook5eapplication.test

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue

class UserStoryFiveDefavouriteASpell {

    private var selectedSpellbook = mutableStateOf<Spellbook?>(null)
    private val spellbooks = mutableStateListOf<Spellbook>()
    private var favouritesSpellbook: Spellbook? = null
    val spellbook1 = Spellbook("Spellbook1").apply { addSpellToSpellbook("Fireball") }
    val spellbook2 = Spellbook("Spellbook2").apply { addSpellToSpellbook("Ice Lance") }
    val favourites = Spellbook("FavouritesTest").apply { addSpellToSpellbook("Healing Word") }

    init {
        prepareSpellbooksForTesting()
    }

    private fun prepareSpellbooksForTesting() {
        // Clear existing spellbooks in the manager
        SpellbookManager.getAllSpellbooks().forEach {
            SpellbookManager.removeSpellbook(it.spellbookName)
        }

        // Add test spellbooks
        SpellbookManager.addSpellbook(spellbook1)
        SpellbookManager.addSpellbook(spellbook2)
        SpellbookManager.addSpellbook(favourites)

        // Assign the favourites spellbook after adding it to the manager
        Log.d("nasdjnasdnjasnj", "favouritesSpellbook contains: $favourites: ")
    }

    @Given("the user has a spell marked as favourite")
    fun the_user_has_a_spell_marked_as_favourite( ) {
        Log.d("Executed5", "Given")
        Log.d("Executed5given", favourites.spells.contains("Healing Word").toString())
        assertTrue("Spellbook Should contain the spell Healing Word", favourites.spells.contains("Healing Word"))
    }

    @When("the tapped spell is already favourited, the spell should be removed from the favourites list")
    fun the_tapped_spell_is_already_favourited_the_spell_should_be_removed_from_the_favourites_list() {
        Log.d("Executed5", "Second When")
        favourites.removeSpell("Healing Word")
        val containsSpell = favourites.spells.contains("Healing Word")
        assertFalse("Favourites spellbook should not contain the marked spell", containsSpell)
    }

    @Then("the favourites list should not contain that spell")
    fun the_favourites_list_should_not_contain_that_spell( ) {
        Log.d("Executed5", "Then")
        val contains = SpellbookManager.getSpellbook("FavouritesTest")?.spells?.contains("Healing Word")
        if (contains != null) {
            assertFalse("Favourites spellbook should not contain the marked spell", contains)
        }

    }
}