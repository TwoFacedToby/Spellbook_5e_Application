package com.example.spellbook5eapplication.androidTest.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList;
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info;
import com.example.spellbook5eapplication.app.Utility.SpellController;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

    class Stepdefs {
        private SpellList list;
        private Spell_Info.SpellInfo actualAnswer = null;

        @Given("a spell list")
        public void a_spell_list() {
            list = SpellController.INSTANCE.getAllSpellsList();
        }

        @When("I open it")
        public void I_open_it() {
            actualAnswer = list.getSpellInfoList().get(0);
        }

        @Then("I should be told {string}")
        public void i_should_be_told() {
            assertNotNull(actualAnswer);
            //assertEquals(!null, actualAnswer);
            //assertEquals(expectedAnswer, actualAnswer);
        }


}
