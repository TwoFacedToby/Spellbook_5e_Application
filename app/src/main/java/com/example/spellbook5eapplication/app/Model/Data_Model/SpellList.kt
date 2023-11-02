package com.example.spellbook5eapplication.app.Model.Data_Model

class SpellList {
    private var indexList: List<String> = emptyList()
    private var spellInfoList: List<Spell_Info.SpellInfo> = emptyList()

    fun setIndexList(names: List<String>) {
        indexList = names
    }
    fun getIndexList(): List<String> {
        return indexList
    }
    fun setSpellInfoList(spellInfo: List<Spell_Info.SpellInfo>) {
        spellInfoList = spellInfo
    }
    fun getSpellInfoList(): List<Spell_Info.SpellInfo> {
        return spellInfoList
    }
    fun printIndexesToConsole(){
        println("Printing spells from list:")
        for(name in indexList){
            println("- $name")
        }
    }
    fun printInfoToConsole(){
        println("Printing Spell Info")
        for(spell in spellInfoList){
            val spellClasses = emptyList<String>().toMutableList()
            for(c in spell.classes!!){
                c.name?.let { spellClasses.add(it) }
            }

            println("${spell.name}")
            println("- Classes: ${spellClasses.toString()}")
            println("- Casting Time: ${spell.castingTime}")
            println("- Duration: ${spell.duration}")
            println("- Spell Level: ${spell.level}")
            println("- Components: ${spell.components}")
        }
        println("Printed ${spellInfoList.size} spells")

    }
}