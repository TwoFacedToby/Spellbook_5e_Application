package com.example.spellbook5eapplication.app.Model

import com.example.spellbook5eapplication.app.Utility.SpellbookManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SpellbookManagerTest {

    private lateinit var spellbookManager: SpellbookManager
    private lateinit var spellbook: Spellbook

    @Before
    fun setup() {
        spellbookManager = SpellbookManager()
        spellbook = Spellbook("Test Spellbook")
    }

    @Test
    fun addSpellbook_and_getSpellbook() {
        spellbookManager.addSpellbook(spellbook)
        assertNotNull("Spellbook should be added", spellbookManager.getSpellbook("Test Spellbook"))
    }

    @Test
    fun removeSpellbook() {
        spellbookManager.addSpellbook(spellbook)
        spellbookManager.removeSpellbook("Test Spellbook")
        assertNull("Spellbook should be removed", spellbookManager.getSpellbook("Test Spellbook"))
    }

    @Test
    fun printAllSpellbooks() {
        spellbook.addSpell("Frostbite")
        spellbookManager.addSpellbook(spellbook)
        // Capture the print output or just ensure no exceptions are thrown
        spellbookManager.printAllSpellbooks()
        // As print statements do not return a value, this test would only fail if an exception is thrown
        assertTrue(true)
    }

    @Test
    fun addSpellToSpellbook(){
        spellbook.addSpell("Fire Ball")
        var spellAddedBoolean = false
        if(spellbook.getSpells()[0].equals("Fire Ball")){
            spellAddedBoolean = true
        }
        assertTrue(spellAddedBoolean)
    }

    @Test
    fun removeSpellFromSpellbook(){
        //First we add a spell, and make sure it's added - then we remove the spell from the spellbook

        spellbook.addSpell("Test Spell")
        assertTrue(spellbook.getSpells()[0].equals("Test Spell"))
        spellbook.removeSpell("Test Spell")
        assertTrue(spellbook.getSpells().isEmpty())
    }
}
