package com.example.spellbook5eapplication.app.Model.Data_Model

import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info

class SpellList {
    private var namesList: List<String> = emptyList()
    private var spellInfoList: List<Spell_Info.SpellInfo> = emptyList()

    fun setSpellNamesList(names: List<String>) {
        namesList = names
    }
    fun getSpellNamesList(): List<String> {
        return namesList
    }
    fun setSpellInfoList(spellInfo: List<Spell_Info.SpellInfo>) {
        spellInfoList = spellInfo
    }
    fun getSpellInfoList(): List<Spell_Info.SpellInfo> {
        return spellInfoList
    }
    fun printNamesToConsole(){
        println("Printing spells from list:")
        for(name in namesList){
            println("- $name")
        }
    }
    fun printInfoToConsole(){
        println("Printing Spell Info")
        for(spell in spellInfoList){
            println("${spell.name}")
            println("- Classes: ${spell.classes}")
            println("- Casting Time: ${spell.castingTime}")
            println("- Duration: ${spell.duration}")
            println("- Spell Level: ${spell.level}")
            println("- Components: ${spell.components}")
        }
        println("Printed ${spellInfoList.size} spells")

    }
}