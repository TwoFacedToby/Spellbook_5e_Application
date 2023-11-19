import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.*;

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList;
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info;
import com.example.spellbook5eapplication.app.view.spellCards.LargeSpellCardKt;
import com.example.spellbook5eapplication.app.viewmodel.GlobalOverlayState;

class OpenSpell {
    static LargeSpellCardKt openSpell(spell Spell_Info.SpellInfoKt {
        return null;
    }
}

public class Stepdefs {


    private globalOverlayState = new GlobalOverlayState();
    private dismiss = null;
    private Spell_Info.SpellInfo spell;
    private LargeSpellCardKt response;

    private SpellList list;

    @Given("I fetch all spells")
    public void I_fetch_all_spells() {
        list = = SpellController.getAllSpellsList()
    }

    @When("I press a spell")
    public void I_press_a_spell() {
        response = LargeSpellCardKt(globalOverlayState, dismiss, list.getSpellInfoList().get("Aid"));
    }

    @Then("I get a overview of the spell")
    public void I_get_a_overview_of_the_spell(LargeSpellCardKt expectedAnswer) {
        assertEquals(expectedAnswer, response);
    }
}