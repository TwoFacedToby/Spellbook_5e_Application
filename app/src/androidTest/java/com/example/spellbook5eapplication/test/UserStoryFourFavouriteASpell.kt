package com.example.spellbook5eapplication.test

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import junit.framework.TestCase.assertFalse
import org.junit.Assert

class UserStoryFourFavouriteASpell {
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

    @Given("the user is viewing a list of spells not favourited")
    fun the_user_is_viewing_a_list_of_spells_not_favourited() {
        Log.d("Executed", "Given")
        Assert.assertNotNull("Favourites spellbook should not be null", favourites)
    }

    @When("the user marks a spell as a favorite by tapping the heart icon")
    fun the_user_marks_a_spell_as_a_favorite_by_tapping_the_heart_icon() {
        Log.d("Executed", "First When")
        val spellToAdd = "Lightning Bolt"
        SpellbookManager.getSpellbook("FavouritesTest")?.addSpellToSpellbook(spellToAdd)
        Log.d("nasdjnasdnjasnj1234", "favouritesSpellbook contains: ${favourites}: ")
        SpellbookManager.saveSpellbookToFile("FavouritesTest")
        }

    @Then("that spell should be saved to a dedicated Favorites section")
    fun that_spell_should_be_saved_to_a_dedicated_favorites_section() {
        Log.d("Executed", "Then")
        val updatedFavouriteSpellbook = favourites.spells?.contains("Lightning Bolt") ?: false
        Log.d("asmodkoasdnk", updatedFavouriteSpellbook.toString())
        Assert.assertTrue("Favourites spellbook should contain the marked spell",
            updatedFavouriteSpellbook
        )
    }


}
