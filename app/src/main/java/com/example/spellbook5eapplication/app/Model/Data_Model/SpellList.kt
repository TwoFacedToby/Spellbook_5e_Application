package com.example.spellbook5eapplication.app.Model.Data_Model

class SpellList {

    private var indexList: List<String> = emptyList()
    private var spellInfoList: List<Spell_Info.SpellInfo> = emptyList()
    private var loaded = 0;

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

    fun setSpellInfoList(spellInfo: List<Spell_Info.SpellInfo>) {
        spellInfoList = spellInfo
    }



    fun getSpellInfoList(): List<Spell_Info.SpellInfo> {
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
            println("- Casting Time: ${spell.castingTime}")
            println("- Duration: ${spell.duration}")
            println("- Spell Level: ${spell.level}")
            println("- Components: ${spell.components}")
        }
        println("Printed ${spellInfoList.size} spells")

    }

    fun createFakeSpellList(): SpellList {
        val fakeSpellList = SpellList()
        val spells = mutableListOf<Spell_Info.SpellInfo>()

        // Creating a few fake spells
        spells.add(Spell_Info.SpellInfo(
            index = "fireball",
            name = "Fireball",
            description = listOf("A bright light emerges."),
            higherLevelDescription = listOf("The light gets brighter."),
            range = "30 feet",
            components = listOf("V"),
            material = null,
            isRitual = false,
            duration = "1 hour",
            isConcentration = false,
            castingTime = "1 action",
            level = 0,
            school = Spell_Info.SpellSchool("evocation", "Evocation", null),
            classes = listOf(Spell_Info.SpellClass("cleric", "Cleric", null)),
            subclasses = listOf(),
            url = null,
            attackType = null,
            damage = null,
            dc = null,
            areaOfEffect = null,
            healAtSlotLevel = null,
            higherLevelAbility = listOf(),
            archetype = null,
            race = null,
            timeOfDay = null,
            circle = null,
            domain = null,
            eldritchInvocations = null,
            patron = null,
            oaths = null,
            sorcerousOrigins = null,
            otherworldlyPatrons = null
        ))
        spells.add(Spell_Info.SpellInfo(
            index = "fire-shield",
            name = "Fire Shield",
            description = listOf("A bright light emerges."),
            higherLevelDescription = listOf("The light gets brighter."),
            range = "30 feet",
            components = listOf("V"),
            material = null,
            isRitual = false,
            duration = "1 hour",
            isConcentration = false,
            castingTime = "1 action",
            level = 0,
            school = Spell_Info.SpellSchool("evocation", "Evocation", null),
            classes = listOf(Spell_Info.SpellClass("cleric", "Cleric", null)),
            subclasses = listOf(),
            url = null,
            attackType = null,
            damage = null,
            dc = null,
            areaOfEffect = null,
            healAtSlotLevel = null,
            higherLevelAbility = listOf(),
            archetype = null,
            race = null,
            timeOfDay = null,
            circle = null,
            domain = null,
            eldritchInvocations = null,
            patron = null,
            oaths = null,
            sorcerousOrigins = null,
            otherworldlyPatrons = null
        ))
        spells.add(Spell_Info.SpellInfo(
            index = "light",
            name = "Light",
            description = listOf("A bright light emerges."),
            higherLevelDescription = listOf("The light gets brighter."),
            range = "30 feet",
            components = listOf("V"),
            material = null,
            isRitual = false,
            duration = "1 hour",
            isConcentration = false,
            castingTime = "1 action",
            level = 0,
            school = Spell_Info.SpellSchool("evocation", "Evocation", null),
            classes = listOf(Spell_Info.SpellClass("cleric", "Cleric", null)),
            subclasses = listOf(),
            url = null,
            attackType = null,
            damage = null,
            dc = null,
            areaOfEffect = null,
            healAtSlotLevel = null,
            higherLevelAbility = listOf(),
            archetype = null,
            race = null,
            timeOfDay = null,
            circle = null,
            domain = null,
            eldritchInvocations = null,
            patron = null,
            oaths = null,
            sorcerousOrigins = null,
            otherworldlyPatrons = null
        ))


        fakeSpellList.setSpellInfoList(spells.toList())
        fakeSpellList.setIndexList(spells.map { it.index!! })

        println("Am I even being returned!? $fakeSpellList")
        return fakeSpellList
    }

    fun getCopy() : SpellList{
        val copy = SpellList();
        copy.loaded = loaded;
        val indexes = emptyList<String>().toMutableList()
        val spellInfos = emptyList<Spell_Info.SpellInfo>().toMutableList()
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
}