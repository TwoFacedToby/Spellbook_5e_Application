package com.example.spellbook5eapplication.test

import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.Search
import com.example.spellbook5eapplication.app.Utility.SpellController
import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import io.cucumber.java.en.And
import io.cucumber.java.en.But
import org.junit.Assert

class UserStoryTwoFilterFunctionality {
    private var fakeSpellList: SpellList? = null

    @Given("the user is viewing a list of spells")
    fun the_user_is_viewing_a_list_of_spells( ) {
        fakeSpellList = SpellList().createFakeSpellList()
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