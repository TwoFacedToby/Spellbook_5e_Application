package com.example.spellbook5eapplication.androidTest.java;

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList;
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info;
import com.example.spellbook5eapplication.app.Utility.SpellController;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OpenSpellActivitySteps {

    Spell_Info.SpellInfo spell;
    Spell_Info.SpellInfo Aid;

    @When("I press a spell")
    public void I_press_a_spell(){
        SpellList spells = SpellController.INSTANCE.getAllSpellsList();
        spell = spells.getSpellInfoList().get(0);
        Aid = spells.getSpellInfoList().get(0);
    }

    @Then("I should see spell info")
    public void I_should_see_spell_info(){
        equals(spell);
    }

}
