package com.example.spellbook5eapplication.app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Utility.SpellController

class SpellViewModel : ViewModel() {
    fun networkRequest(callback: (result: SpellList) -> Unit){
        val controller = SpellController()
        val spellList = controller.getAllSpellsList()
        if(spellList != null){
            controller.loadSpellList(spellList)
            val filter = exampleFilter()
            controller.searchSpellListWithFilter(spellList, filter)
            callback(spellList)
        }
    }
    fun exampleFilter() : Filter {
        val filter = Filter()
        filter.setSpellName("Fire")
        return filter
    }
}