package com.dtu.uemad.birthdaycardtest.Model

import com.dtu.uemad.birthdaycardtest.Model.Data_Model.SpellList
import com.dtu.uemad.birthdaycardtest.Model.Data_Model.Spell_Info

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

    /**@author Tobias s224271
     * @param spellList the list that should be searched
     * @param filter the SpellInfo data class that has the same properties as the spell you want to find
     * @return A SpellList that only contains the spells that has the same properties as the filter
     *
     * Checks to see if the filter doesnt have null value for the properties we are filtering for, if it is not a null value, then
     * it should remove all the spells from the spellList that doesn't have the same value.
     * It checks the more obscure filters first as that will remove a lot so later it wont have to go through that many.
     */
    fun searchSpellListWithFilter(spellList: SpellList, filter : Spell_Info.SpellInfo) : SpellList {
        var changingSpellList = spellList
        if(filter.name != null) changingSpellList = searchSpellList(changingSpellList, filter.name)
        if(filter.castingTime != null) removeAllButFilter(spellList, "casting_time", filter)
        if(filter.areaOfEffect != null) removeAllButFilter(spellList, "aoe", filter)
        if(filter.duration != null) removeAllButFilter(spellList, "duration", filter)
        if(filter.components != null) removeAllButFilter(spellList, "components", filter)
        if(filter.school != null) removeAllButFilter(spellList, "school", filter)
        if(filter.isRitual != null) removeAllButFilter(spellList, "ritual", filter)
        if(filter.damage != null) removeAllButFilter(spellList, "damage", filter)
        if(filter.level != null) removeAllButFilter(spellList, "level",filter)
        if(filter.classes != null) removeAllButFilter(spellList, "classes", filter)
        if(filter.dc != null) removeAllButFilter(spellList, "dc", filter)
        if(filter.isConcentration != null) removeAllButFilter(spellList, "concentration", filter)
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
    private fun removeAllButFilter(spellList : SpellList, type : String, filter : Spell_Info.SpellInfo){
        val list = spellList.getSpellInfoList().toMutableList()
        when (type) {
            "level" -> {
                for(element in list){
                    if(element.level != filter.level) list.remove(element)
                }
            }
            "casting_time" ->{
                for(element in list){
                    if(element.castingTime != filter.castingTime) list.remove(element)
                }
            }
            "school" ->{
                for(element in list){
                    if(element.school != filter.school) list.remove(element)
                }
            }
            "concentration" ->{
                for(element in list){
                    if(element.isConcentration != filter.isConcentration) list.remove(element)
                }
            }
            "ritual" ->{
                for(element in list){
                    if(element.isRitual != filter.isRitual) list.remove(element)
                }
            }
            "aoe" ->{
                for(element in list){
                    if(element.areaOfEffect != filter.areaOfEffect) list.remove(element)
                }
            }
            "components" ->{
                for(element in list){
                    if(element.components != filter.components) list.remove(element)
                }
            }
            "dc" ->{
                for(element in list){
                    if(element.dc != filter.dc) list.remove(element)
                }
            }
            "duration" ->{
                for(element in list){
                    if(element.duration != filter.duration) list.remove(element)
                }
            }
            "classes" ->{
                for(element in list){
                    if(element.classes != filter.classes) list.remove(element)
                }
            }
            "damage" ->{
                for(element in list){
                    if(element.damage != filter.damage) list.remove(element)
                }
            }
            else -> { // Note the block
                println("Not a filter")
            }
        }
        spellList.setSpellInfoList(list.toList())
    }





}