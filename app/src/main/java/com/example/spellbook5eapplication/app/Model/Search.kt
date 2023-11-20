package com.example.spellbook5eapplication.app.Model

import androidx.compose.ui.text.toLowerCase
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import java.lang.Character.toLowerCase
import java.util.Locale

class Search {
    /**@author Tobias s224271
     * @param spellList The list that should be searched
     * @param searchString The String given from the user to search toward, it can be multiple words and it will search all of them.
     * @return A spellList of spells that contains one of the words in the string
     *
     * It gets the list of names of all the spells in the spell list.
     * in the creates a String array from the searchString by splitting with spacebar
     * creates a spelllist and a MutableList of Strings
     * It then goes through all the names in the spell list, checks if any of the keywords is contained in the name of the spell
     * if yes than it's added to the spellList and then breaks the inner for loop. Making sure any spell can only be added once,
     * even though it matches multiple keywords.
     * Then it converts the MutableList into a list and adds it to the spell list that it returns.
     */
    fun searchSpellNames(spellList : SpellList, searchString : String) : SpellList {
        val namesList = spellList.getIndexList()
        val keywords = searchString.split(" ")
        val matches = mutableListOf<String>()
        for(name in namesList){
            for(keyword in keywords){
                if(keyword.lowercase() in name.lowercase()){
                    matches.add(name)
                    break
                }
            }
        }
        spellList.setIndexList(matches.toList())
        matchLists(spellList)
        return sortSpellsDueToKeywordRelevance(keywords, spellList)
    }
    /**@author Tobias s224271
     * @param spellList The list that should be matched
     *
     * Because the searchSpellNames function only searches for names, we also need to reduce the spellInfoList, for this we just make sure they contain the same spells.
     * It just makes sure we can search spells without needing to also reduce the spellInfoList as we want those two to be split up.
     */
    private fun matchLists(spellList : SpellList){
        if(spellList.getSpellInfoList().isEmpty()) return
        val names = spellList.getIndexList()
        val spells = mutableListOf<Spell_Info.SpellInfo>()
        for(info in spellList.getSpellInfoList()){
            if(info.index != null)
                if(names.contains(info.index))
                    spells.add(info)
        }
        spellList.setSpellInfoList(spells.toList())
    }
    /**@author Tobias s224271
     * @param spellList The list that should be searched
     * @param filter The filter that decide what spells should be returned
     *
     * Checks a range of different variables, first if there has been set a filter for it, then afterward calls the filterSpells function.
     * At each different type of variable the spellList is shortened, until at then end, only the spells that comply with all filters has gotten through.
     * The order of variables goes to roughly what varaibles has the most different values, so we can get as many out as quickly as possible.
     * Example: We search name first because that is the most likely to be different and shortens the search from going through up to 319 spells down to a rather small number.
     */

    fun searchSpellListWithFilter(spellList: SpellList, filter : Filter) : SpellList {
        var changingSpellList = spellList
        if(filter.getSpellName() != "") changingSpellList = searchSpellNames(changingSpellList, filter.getSpellName())
        if(filter.getCastingTime().isNotEmpty()) filterSpells(spellList, "casting_time", filter)
        if(filter.getAreaOfEffect().isNotEmpty()) filterSpells(spellList, "aoe", filter)
        if(filter.getDuration().isNotEmpty()) filterSpells(spellList, "duration", filter)
        if(filter.getComponent().isNotEmpty()) filterSpells(spellList, "components", filter)
        if(filter.getSchool().isNotEmpty()) filterSpells(spellList, "school", filter)
        if(filter.getIsRitual().isNotEmpty()) filterSpells(spellList, "ritual", filter)
        if(filter.getDamageType().isNotEmpty()) filterSpells(spellList, "damage", filter)
        if(filter.getLevel().isNotEmpty()) filterSpells(spellList, "level", filter)
        if(filter.getClasses().isNotEmpty()) filterSpells(spellList, "classes", filter)
        if(filter.getIsConcentration().isNotEmpty()) filterSpells(spellList, "concentration", filter)
        return changingSpellList
    }

    /**@author Tobias s224271
     * @param spellList The list that should be searched
     * @param type  The type of filter it should remove from
     * @param filter The filter that it needs the specific value from
     *
     * Checks makes a mutableList where it goes through it one by one to check if the spell has the same value as the filter value in that category.
     * If it doesnt have the same, it will be removed from the list.
     * The MutableList is then converted back to a List, and put into the spellList, so it now contains fewer spells for the next filter or finished list.
     */
    private fun filterSpells(spellList : SpellList, type : String, filter : Filter){
        val list = spellList.getSpellInfoList().toMutableList()
        when (type) {
            "level" -> {
                var index = 0
                while(index < list.size){
                    if(!filter.getLevel().contains(list[index].level)) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "casting_time" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(time in filter.getCastingTime()){
                        if(lower(time.value) == lower(list[index].castingTime)) {
                            found = true
                            break
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "school" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(school in filter.getSchool()){
                        if(lower(school.value) == lower(list[index].school?.name)) {
                            found = true
                            break
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "concentration" ->{
                var index = 0
                while(index < list.size){
                    if(!filter.getIsConcentration().contains(list[index].isConcentration)) {
                        list.removeAt(index)
                    }
                    else{
                        index++
                    }
                }
            }
            "ritual" ->{
                var index = 0
                while(index < list.size){
                    if(!filter.getIsRitual().contains(list[index].isRitual)) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "aoe" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(aoe in filter.getAreaOfEffect()){
                        if(lower(aoe.value) == lower(list[index].areaOfEffect?.type)) {
                            found = true
                            break
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "components" ->{
                var index = 0
                while(index < list.size){
                    var found = true
                    for(component in filter.getComponent()){
                        if(list[index].components?.contains(component.value) == false){
                            found = false
                            break
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "duration" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(duration in filter.getDuration()){
                        if(lower(duration.value)?.let { lower(list[index].duration)?.contains(it) } == true) {
                            found = true
                            break
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "classes" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(filterClass in filter.getClasses()){
                        for(spellClass in list[index].classes!!){
                            if(lower(spellClass.name) == lower(filterClass.name)){
                                found = true
                                break
                            }
                        }
                    }
                    if(!found) list.remove(list[index])
                    else{
                        index++
                    }
                }
            }
            "damage" ->{
                var index = 0
                while(index < list.size){
                    var found = false
                    for(damage in filter.getDamageType()){
                        if(lower(damage.value) == lower((list[index].damage?.damageType?.name))) {
                            found = true
                            break
                        }
                    }
                    if(!found) list.remove(list[index])else{
                        index++
                    }
                }
            }
            else -> { // Note the block
                println("Not a filter")
            }
        }
        spellList.setSpellInfoList(list.toList())
    }

    private fun lower(value : String?) : String? {
        return value?.lowercase(Locale.ROOT)
    }


    private fun sortSpellsDueToKeywordRelevance(keywords : List<String>, spellList : SpellList) : SpellList {
        var indexes = spellList.getIndexList().toMutableList()
        var infos = spellList.getSpellInfoList().toMutableList()
        val relevances : MutableList<Relevance> = emptyList<Relevance>().toMutableList()

        for(i in indexes.indices){
            val relevance = Relevance()
            relevances.add(relevance.get(indexes[i], infos[i], keywords))
        }
        val sorted = relevances.sortedBy { it.getNoFits() }

        indexes.clear()
        infos.clear()
        for(item in sorted){
            indexes.add(item.getSpell())
            infos.add(item.getInfo()!!)
        }

        spellList.setIndexList(indexes)
        spellList.setSpellInfoList(infos)

        return spellList
    }

    class Relevance (){
        private var spell : String = ""
        private var spellInfo : Spell_Info.SpellInfo? = null
        private var name : List<String> = emptyList()

        private var noFits : Int = 0
        fun get(spell : String, info : Spell_Info.SpellInfo, keywords: List<String>) : Relevance {
            this.spellInfo = info
            this.spell = spell
            this.name = spell.split("-")

            for(keyword in keywords){
                if(name.contains(keyword.lowercase()) && name.size > 1) noFits -= 20
                for(n in name){
                    for(index in keyword.indices){

                        if(n.length > index){
                            if(keyword[index] == n[index])
                            else noFits++
                        }
                        else noFits++
                    }
                }

            }
            return this

        }

        fun getNoFits() : Int{
            return noFits
        }
        fun getSpell() : String{
            return spell
        }
        fun getInfo() : Spell_Info.SpellInfo? {
            return spellInfo
        }



    }

}