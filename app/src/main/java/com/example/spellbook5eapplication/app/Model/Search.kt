package com.example.spellbook5eapplication.app.Model

import androidx.compose.ui.text.toLowerCase
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
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
    fun searchSpellList(spellList : SpellList, searchString : String) : SpellList {
        val namesList = spellList.getSpellNamesList()
        val keywords = searchString.split(" ")
        val toReturn = SpellList()
        val matches = mutableListOf<String>()
        for(name in namesList){
            for(keyword in keywords){
                if(keyword in name){
                    matches.add(name)
                    break
                }
            }
        }
        toReturn.setSpellNamesList(matches.toList())
        return toReturn
    }
    fun searchSpellListWithFilter(spellList: SpellList, filter : Filter) : SpellList {
        var changingSpellList = spellList
        if(filter.getSpellName() != "") changingSpellList = searchSpellList(changingSpellList, filter.getSpellName())
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

}