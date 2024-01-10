package com.example.spellbook5eapplication.app.Model.Data_Model

import android.util.Log
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell.SpellInfo
import com.example.spellbook5eapplication.app.Utility.SpellDataFetcher
import kotlin.math.min


class SpellList {

    private val spellInfoMap = HashMap<String, SpellInfo>()
    private var indexList: List<String> = emptyList()
    private var spellInfoList: List<SpellInfo> = emptyList()
    private var loaded = 0;


    // Method to add SpellInfo objects to the map
    fun addSpellInfo(spellInfo: SpellInfo) {
        spellInfo.index?.let { index ->
            spellInfoMap.putIfAbsent(index, spellInfo)
        }
    }

    fun getSpellInfo(index: String?): SpellInfo? {
        return spellInfoMap[index]
    }

    fun hasSpellInfo(index: String?): Boolean {
        return spellInfoMap.containsKey(index)
    }

    fun loadSpellInfos(spellInfos: List<SpellInfo?>) {
        for (spellInfo in spellInfos) {
            addSpellInfo(spellInfo!!)
        }
    }

    suspend fun getOrFetchSpellInfo(index: String): SpellInfo? {
        Log.d("SpellList", spellInfoMap.size.toString() + " spells loaded and was it already in map: "+ spellInfoMap.containsKey(index))
        return spellInfoMap[index] ?: SpellDataFetcher.localOrAPI(index)?.also { spellInfo ->
            spellInfoMap[index] = spellInfo
        }
    }

    fun getLoaded(): Int {
        return loaded
    }

    fun setLoaded(loaded: Int) {
        this.loaded = loaded
    }


    fun setIndexList(names: List<String>) {
        indexList = names
    }


    fun getIndexList(): List<String> {
        return indexList
    }

    fun setSpellInfoList(spellInfo: List<Spell.SpellInfo>) {
        spellInfoList = spellInfo
    }



    fun getSpellInfoList(): List<Spell.SpellInfo> {
        return spellInfoList
    }
     /*
    private var indexList: MutableList<String> = mutableListOf()
    private var spellInfoList: MutableList<Spell_Info.SpellInfo> = mutableListOf()
    private var loaded = 0;

    fun getLoaded() : Int{
        return loaded
    }
    fun setLoaded(loaded : Int){
        this.loaded = loaded
    }
    fun setIndexList(names: List<String>) {
        indexList = names.toMutableList()
    }
    fun getIndexList(): List<String> {
        return indexList
    }
    fun setSpellInfoList(spellInfo: List<Spell_Info.SpellInfo>) {
        spellInfoList = spellInfo.toMutableList()
    }
    fun getSpellInfoList(): List<Spell_Info.SpellInfo> {
        return spellInfoList
    }

      */
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
            println("- Casting Time: ${spell.casting_time}")
            println("- Duration: ${spell.duration}")
            println("- Spell Level: ${spell.level}")
            println("- Components: ${spell.components}")
        }
        println("Printed ${spellInfoList.size} spells")

    }


    fun getCopy() : SpellList{
        val copy = SpellList();
        copy.loaded = loaded;
        val indexes = emptyList<String>().toMutableList()
        val spellInfos = emptyList<Spell.SpellInfo>().toMutableList()
        for(spell in indexList){
            indexes.add(spell)
        }
        for(spell in spellInfoList){
            spellInfos.add(spell)
        }
        copy.setIndexList(indexes)
        copy.setSpellInfoList(spellInfos)
        return copy;
    }

    override fun toString(): String {
        return "SpellList(indexList=$indexList, spellInfoList=$spellInfoList, loaded=$loaded)"
    }

    fun copy() : SpellList {
        val indexListNew = mutableListOf<String>()
        indexList.forEach {
            indexListNew.add(it)
        }
        val infoListNew = mutableListOf<Spell.SpellInfo>()
        spellInfoList.forEach {
            infoListNew.add(it)
        }

        val spellList = SpellList()
        spellList.loaded = loaded
        spellList.indexList = indexListNew
        spellList.spellInfoList = infoListNew
        return spellList
    }

}