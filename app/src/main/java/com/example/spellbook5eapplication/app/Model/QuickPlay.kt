package com.example.spellbook5eapplication.app.Model

import kotlin.math.roundToInt

object QuickPlay {


    fun getQuickPlayList(c : Class, lvl : Int) : List<String>{
        val list = when(c){
            Class.BARD -> getBard(getSpellLevelFromCharLevel(c, lvl))
            Class.CLERIC -> getCleric(getSpellLevelFromCharLevel(c, lvl))
            Class.DRUID -> getDruid(getSpellLevelFromCharLevel(c, lvl))
            Class.FIGHTER -> getFighter(getSpellLevelFromCharLevel(c, lvl))
            Class.PALADIN -> getPaladin(getSpellLevelFromCharLevel(c, lvl))
            Class.RANGER -> getRanger(getSpellLevelFromCharLevel(c, lvl))
            Class.ROGUE -> getRogue(getSpellLevelFromCharLevel(c, lvl))
            Class.BARBARIAN -> getBarbarian(getSpellLevelFromCharLevel(c, lvl))
            Class.MONK -> getMonk(getSpellLevelFromCharLevel(c, lvl))
            Class.WARLOCK -> getWarlock(getSpellLevelFromCharLevel(c, lvl))
            Class.WIZARD -> getWizard(getSpellLevelFromCharLevel(c, lvl))
            Class.ARTIFICER -> getArtificer(getSpellLevelFromCharLevel(c, lvl))
            Class.SORCERER -> getSorcerer(getSpellLevelFromCharLevel(c, lvl))
        }
        return list.toList()
    }

    private fun getSpellLevelFromCharLevel(c : Class, lvl : Int) : Int{
        return when(c){
            Class.BARD -> (lvl / 2f).roundToInt()
            Class.CLERIC -> (lvl / 2f).roundToInt()
            Class.DRUID -> (lvl / 2f).roundToInt()
            Class.FIGHTER -> {
                if(lvl < 3) 0
                else if(lvl < 7) 1
                else if(lvl < 13) 2
                else if(lvl < 19) 3
                else 4
            }
            Class.PALADIN -> {
                if(lvl < 2) 0
                else if(lvl < 5) 1
                else if(lvl < 9) 2
                else if(lvl < 13) 3
                else if(lvl < 17) 4
                else 5
            }
            Class.RANGER -> {
                if(lvl < 2) 0
                else if(lvl < 5) 1
                else if(lvl < 9) 2
                else if(lvl < 13) 3
                else if(lvl < 17) 4
                else 5
            }
            Class.ROGUE -> {
                if(lvl < 3) 0
                else if(lvl < 7) 1
                else if(lvl < 13) 2
                else if(lvl < 19) 3
                else 4
            }
            Class.BARBARIAN -> 0
            Class.MONK -> 0
            Class.WARLOCK -> {
                if(lvl < 3) 1
                else if(lvl < 5) 2
                else if(lvl < 7) 3
                else if(lvl < 9) 4
                else 5
            }
            Class.WIZARD -> (lvl / 2f).roundToInt()
            Class.ARTIFICER -> {
                if(lvl < 5) 1
                else if(lvl < 9) 2
                else if(lvl < 13) 3
                else if(lvl < 17) 4
                else 5
            }
            Class.SORCERER -> (lvl / 2f).roundToInt()
        }
    }

    private fun getBard(lvl : Int) : MutableList<String>{
        val list = mutableListOf<String>()
        if(lvl < 1) return list
        //Cantrips
        list.add("dancing-lights")
        list.add("minor-illusion")
        list.add("vicious-mockery")
        //1st Levels
        list.add("charm-person")
        list.add("cure-wounds")
        list.add("disguise-self")

        if(lvl < 2) return list
        //2nd Levels
        list.add("enhance-ability")
        list.add("hold-person")
        list.add("invisibility")
        if(lvl < 3) return list
        //3rd Level
        list.add("hypnotic-pattern")
        list.add("sending")
        list.add("tongues")

        if(lvl < 4) return list
        //4th Level
        list.add("confusion")
        list.add("dimension-door")
        list.add("polymorph")
        if(lvl < 5) return list
        //5th Level
        list.add("dominate-person")
        list.add("mass-cure-wounds")
        list.add("seeming")
        return list
    }

    private fun getCleric(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //Cantrips
        list.add("guidance")
        list.add("mending")
        list.add("sacred-flame")
        //1st Levels
        list.add("bless")
        list.add("cure-wounds")
        list.add("guiding-bolt")

        if (lvl < 2) return list
        //2nd Levels
        list.add("aid")
        list.add("prayer-of-healing")
        list.add("zone-of-truth")

        if (lvl < 3) return list
        //3rd Level
        list.add("bestow-curse")
        list.add("revivify")
        list.add("spirit-guardians")

        if (lvl < 4) return list
        //4th Level
        list.add("banishment")
        list.add("control-water")
        list.add("guardian-of-faith")

        if (lvl < 5) return list
        //5th Level
        list.add("geas")
        list.add("mass-cure-wounds")
        list.add("planar-binding")
        return list
    }

    private fun getDruid(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //Cantrips
        list.add("druidcraft")
        list.add("guidance")
        list.add("poison-spray")

        //1st Levels
        list.add("animal-friendship")
        list.add("faerie-fire")
        list.add("goodberry")

        if (lvl < 2) return list
        //2nd Levels
        list.add("animal-messenger")
        list.add("heat-metal")
        list.add("locate-object")

        if (lvl < 3) return list
        //3rd Level
        list.add("conjure-animals")
        list.add("plant-growth")
        list.add("water-breathing")
        if (lvl < 4) return list
        //4th Level
        list.add("conjure-minor-elemental")
        list.add("control-water")
        list.add("polymorph")

        if (lvl < 5) return list
        //5th Level
        list.add("commune-with-nature")
        list.add("insect-plague")
        list.add("tree-stride")

        return list
    }

    private fun getFighter(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //Cantrips
        list.add("true-strike")
        list.add("shocking-grasp")
        list.add("ray-of-frost")
        //1st Levels
        list.add("burning-hands")
        list.add("grease")
        list.add("jump")
        if (lvl < 2) return list
        //2nd Levels
        list.add("blur")
        list.add("darkness")
        list.add("flaming-sphere")

        if (lvl < 3) return list
        //3rd Level
        list.add("counterspell")
        list.add("fireball")
        list.add("fly")
        if (lvl < 4) return list
        //4th Level
        list.add("confusion")
        list.add("fire-shield")
        list.add("stoneskin")
        return list
    }

    private fun getPaladin(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //1st Levels
        list.add("bless")
        list.add("divine-favor")
        list.add("shield-of-faith")

        if (lvl < 2) return list
        //2nd Levels
        list.add("find-steed")
        list.add("magic-weapon")
        list.add("zone-of-truth")
        if (lvl < 3) return list
        //3rd Level
        list.add("daylight")
        list.add("dispel-magic")
        list.add("remove-curse")

        if (lvl < 4) return list
        //4th Level
        list.add("banishment")
        list.add("death-ward")
        if (lvl < 5) return list
        //5th Level
        list.add("dispel-evil-and-good")
        return list
    }

    private fun getRanger(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //1st Levels
        list.add("goodberry")
        list.add("hunters-mark")
        list.add("speak-with-animals")
        if (lvl < 2) return list
        //2nd Levels
        list.add("find-traps")
        list.add("pass-without-trace")
        list.add("spike-growth")

        if (lvl < 3) return list
        //3rd Level
        list.add("conjure-animals")
        list.add("water-walk")
        list.add("wind-wall")


        if (lvl < 4) return list
        //4th Level
        list.add("freedom-of-movement")
        list.add("locate-creature")
        list.add("stoneskin")

        if (lvl < 5) return list
        //5th Level
        list.add("tree-stride")
        list.add("commune-with-nature")

        return list
    }

    private fun getRogue(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        //Cantrips

        if (lvl < 1) return list
        //1st Levels

        if (lvl < 2) return list
        //2nd Levels

        if (lvl < 3) return list
        //3rd Level

        if (lvl < 4) return list
        //4th Level

        if (lvl < 5) return list
        //5th Level

        return list
    }

    private fun getBarbarian(lvl: Int): MutableList<String> {
        return mutableListOf<String>() //Has no spells
    }

    private fun getMonk(lvl: Int): MutableList<String> {
        return mutableListOf<String>() //Has no spells
    }

    private fun getWarlock(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()
        if (lvl < 1) return list
        //Cantrips
        list.add("eldritch-blast")
        list.add("poison-spray")
        list.add("prestidigitation")
        //1st Levels
        list.add("hellish-rebuke")
        list.add("illusory-script")
        list.add("unseen-servant")
        if (lvl < 2) return list
        //2nd Levels
        list.add("darkness")
        list.add("hold-person")
        list.add("misty-step")
        if (lvl < 3) return list
        //3rd Level
        list.add("fear")
        list.add("fly")
        list.add("vampiric-touch")
        if (lvl < 4) return list
        //4th Level
        list.add("banishment")
        list.add("blight")
        list.add("dimension-door")

        if (lvl < 5) return list
        //5th Level
        list.add("hold-monster")
        list.add("contact-other-plane")
        list.add("scrying")
        return list
    }

    private fun getWizard(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()

        if (lvl < 1) return list
        //Cantrips
        list.add("message")
        list.add("minor-illusion")
        list.add("fire-bolt")
        list.add("shocking-grasp")
        //1st Levels
        list.add("burning-hands")
        list.add("feather-fall")
        list.add("find-familiar")
        list.add("grease")
        list.add("hideous-laughter")
        list.add("shield")

        if (lvl < 2) return list
        //2nd Levels
        list.add("acid-arrow")
        list.add("arcane-lock")
        list.add("flaming-sphere")

        if (lvl < 3) return list
        //3rd Level
        list.add("animate-dead")
        list.add("counter-spell")
        list.add("fireball")

        if (lvl < 4) return list
        //4th Level
        list.add("black-tentacles")
        list.add("control-water")
        list.add("fire-shield")

        if (lvl < 5) return list
        //5th Level
        list.add("animate-objects")
        list.add("cone-of-cold")
        list.add("modify-memory")

        return list
    }

    private fun getArtificer(lvl: Int): MutableList<String> {
        return mutableListOf<String>() //Not in SRD 5e
    }

    private fun getSorcerer(lvl: Int): MutableList<String> {
        val list = mutableListOf<String>()

        if (lvl < 1) return list
        //Cantrips
        list.add("chill-touch")
        list.add("poison-spray")
        list.add("prestidigitation")

        //1st Levels
        list.add("charm-person")
        list.add("comprehend-languages")
        list.add("detect-magic")
        list.add("disguise-self")

        if (lvl < 2) return list
        //2nd Levels
        list.add("blur")
        list.add("invisibility")
        list.add("misty-step")

        if (lvl < 3) return list
        //3rd Level
        list.add("dispel-magic")
        list.add("fireball")
        list.add("haste")

        if (lvl < 4) return list
        //4th Level
        list.add("dominate-beast")
        list.add("polymorph")
        list.add("stoneskin")

        if (lvl < 5) return list
        //5th Level
        list.add("cloudkill")
        list.add("creation")
        list.add("teleportation-circle")

        return list
    }






    enum class Class{
        BARD,
        CLERIC,
        DRUID,
        FIGHTER,
        PALADIN,
        RANGER,
        ROGUE,
        BARBARIAN,
        MONK,
        WARLOCK,
        WIZARD,
        ARTIFICER,
        SORCERER
    }
}