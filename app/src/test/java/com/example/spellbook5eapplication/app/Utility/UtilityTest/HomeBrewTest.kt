package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.HomeBrewManager
import com.example.spellbook5eapplication.app.Utility.SpellController
import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class HomeBrewTest {

    private lateinit var spell: Spell_Info.SpellInfo
    private lateinit var homeManager: HomeBrewManager
    // parts of a spell
    private val name = "Fireball"
    private val comp: List<String> = listOf("V", "", "M")

    @Before
    fun setup() {
        homeManager = HomeBrewManager()
    }

    @Test
    fun SaveSpell() {
        homeManager.createSpellJson(name , "The most netorious spell of all to test", "throw distance", comp, true, false, "ten minutes",
            "Instant", 2)
        assertNotNull(SpellController.retrieveHomeBrew()!!.getSpellInfoList())
        //assertEquals(name, SpellController.retrieveHomeBrew()!!.getSpellInfoList().get(0).name)
    }

}
