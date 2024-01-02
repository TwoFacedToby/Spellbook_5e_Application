package com.example.spellbook5eapplication.app.viewmodel

import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController

class SpellQueryViewModel(var spellList: SpellList? = null) {
    fun nextSpell(spellList: SpellList) : List<Spell_Info.SpellInfo?>?{

        var nextSpells = SpellController.loadNextFromSpellList(10, spellList)

        return nextSpells
    }

    fun totalSpellsLoaded() : Int {
        var totalSpellsToLoad = spellList!!.getIndexList().size
        return totalSpellsToLoad
    }


}