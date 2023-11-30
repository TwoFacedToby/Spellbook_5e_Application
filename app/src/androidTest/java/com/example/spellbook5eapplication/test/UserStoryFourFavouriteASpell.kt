package com.example.spellbook5eapplication.test

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Spellbook
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import com.example.spellbook5eapplication.app.Utility.SpelllistLoader
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.cucumber.java.en.And
import io.cucumber.java.en.But
import org.junit.Assert

class UserStoryFourFavouriteASpell {

    private var selectedSpellbook = mutableStateOf<Spellbook?>(null)
    private val spellbooks = mutableStateListOf<Spellbook>()
    private var selectedSpellbookTemp: Spellbook? = null
    private var fakeSpellList: SpellList? = null
    var favouritesSpellbook: Spellbook? = null
    var beforeAddingSize: Int? = null
    var checkSpellNotInFavourites: String? = null
    var checkSpellInFavourites: Boolean? = false
    var checkSpellRemovedFromFavourites: Boolean? = false

    fun loadSpellbooks() {
        SpelllistLoader.loadSpellbooks()
        spellbooks.clear()
        spellbooks.addAll(SpellbookManager.getAllSpellbooks())
        selectedSpellbook.value = spellbooks.firstOrNull()
        favouritesSpellbook = SpellbookManager.getSpellbook("Favourites")
    }

    @Given("the user is viewing a list of spells not favourited")
    fun the_user_is_viewing_a_list_of_spells_not_favourited() {
        loadSpellbooks()
        fakeSpellList = SpellList().createFakeSpellList()

        //Remove spell from favourites if it is there, before we move on to be able to test it.
        if (fakeSpellList!!.getIndexList().size > 1) {
            checkSpellNotInFavourites = fakeSpellList!!.getIndexList().get(1)
            favouritesSpellbook?.spells?.remove(checkSpellNotInFavourites)
        }
    }

    @When("the user marks a spell as a favorite by tapping the heart icon")
    fun the_user_marks_a_spell_as_a_favorite_by_tapping_the_heart_icon() {
        loadSpellbooks()
        beforeAddingSize = favouritesSpellbook?.spells?.size

        favouritesSpellbook?.spells?.add(fakeSpellList!!.getIndexList().get(1))
        if(favouritesSpellbook?.spells?.size == (beforeAddingSize?.plus(1))){
            if(favouritesSpellbook!!.spells.contains(fakeSpellList!!.getIndexList().get(1))){
                checkSpellInFavourites = true
                println("Spell added to favourites.")
            } else {
                println("Spell not added to favourites.")
        }
    }
}

    @When("the tapped spell is already favourited, the spell should be removed from the favourites list")
    fun the_tapped_spell_is_already_favourited_the_spell_should_be_removed_from_the_favourites_list(){
        loadSpellbooks()
        beforeAddingSize = favouritesSpellbook?.spells?.size

        favouritesSpellbook?.spells?.remove(fakeSpellList!!.getIndexList().get(1))
        if(favouritesSpellbook?.spells?.size == (beforeAddingSize?.minus(1))){
            if(!(favouritesSpellbook!!.spells.contains(fakeSpellList!!.getIndexList().get(1)))){
                checkSpellRemovedFromFavourites = true
                println("Spell removed from favourites.")
            } else {
                println("Spell wasn't removed from favourites.")
            }
        }
        Assert.assertTrue(checkSpellRemovedFromFavourites!!)
    }
    @Then("that spell should be saved to a dedicated Favorites section")
    fun that_spell_should_be_saved_to_a_dedicated_favorites_section( ) {
        Assert.assertTrue(checkSpellInFavourites!!)
    }
}