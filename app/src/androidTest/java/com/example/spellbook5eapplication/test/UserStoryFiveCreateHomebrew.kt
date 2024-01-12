package com.example.spellbook5eapplication.test

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook
import com.example.spellbook5eapplication.app.Repository.SpellbookManager
import com.example.spellbook5eapplication.app.Repository.SpelllistLoader
import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
import com.example.spellbook5eapplication.app.viewmodel.CreateSpellViewModel
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Assert

class UserStoryFiveCreateHomebrew {

    private lateinit var userSpell: Spell.SpellInfo
    private val create = CreateSpellViewModel()

    @Given("the user has an idea for a new homebrew spell")
    fun the_user_has_an_idea_for_a_new_homebrew_spell() {
        // Its a new idea so no old exists
        LocalDataLoader.deleteFile("cucumber spell.json", LocalDataLoader.DataType.HOMEBREW)

        userSpell = Spell.SpellInfo(
            index = "cucumber spell",
            name = "Cucumber spell",
            desc = listOf("A cucumber spell is a tool for testing\n cucumbers are fruits!"),
            level = 1,
            atHigherLevel = listOf("Get a tomato instead \n tomatoes are also fruits!"),
            range = "within test scope",
            duration = "ETERNAL! (not \"a TURTEL!\")",
            components = listOf("V","M"),
            materials = "A cucumber... DUh!",
            ritual = true,
            concentration = false,
            school = Spell.SpellSchool(index = "necromancy", name = "Necromancy", url = "nec"),
            classes = listOf(Spell.SpellClass(index = "wizard", name = "Wizard", url = "wiz")),
            attackType = "Wet",
            damage = Spell.SpellDamage(Spell.SpellDamageType(index = "hurty", name = "Hurty")),
            casting_time = "Instant",
            url = "Cucumber",
            homebrew = true,
            dc = "Easy",
            json = "CUCUMBERS ARE WITH SALT ON TASTES GREAT. SERIOUS GUYS ITS GOOD!"
        )

    }

    @When("the user writes all the spell information down")
    fun the_user_writes_all_the_spell_information_down() {

        create.updateEntireSpell(userSpell)

}

    @When("create the spell")
    fun create_the_spell(){

        create.saveSpell()

    }
    @Then("that spell should exist localy")
    fun that_spell_should_exist_localy( ) {

        Assert.assertNotNull( LocalDataLoader.getJson("cucumber spell.json", LocalDataLoader.DataType.HOMEBREW) )

    }
}