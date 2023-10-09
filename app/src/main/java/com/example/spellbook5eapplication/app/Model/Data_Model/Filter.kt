package com.example.spellbook5eapplication.app.Model.Data_Model

class Filter {
    private var spellName = ""
    private val school = mutableListOf<School>()
    private val castingTime = mutableListOf<Casting_Time>()
    private val aoe = mutableListOf<Area_Of_Effect>()
    private val duration = mutableListOf<Duration>()
    private val components = mutableListOf<Component>()
    private val ritual  = mutableListOf<Boolean>()
    private val damage = mutableListOf<Damage_Type>()
    private val level = mutableListOf<Int>()
    private val classes = mutableListOf<Classes>()
    private val concentration  = mutableListOf<Boolean>()

    fun setSpellName(name : String){
        this.spellName = name
    }
    fun getSpellName() : String{
        return spellName
    }
    fun addSchool(school: School){
        if(!this.school.contains(school)) this.school.add(school)
    }
    fun removeSchool(school : School){
        if(this.school.contains(school)) this.school.remove(school)
    }
    fun clearSchool(){
        this.school.clear()
    }
    fun getSchool() : List<School>{
        return this.school.toList()
    }
    fun addCastingTime(castingTime: Casting_Time){
        if(!this.castingTime.contains(castingTime)) this.castingTime.add(castingTime)
    }
    fun removeCastingTime(castingTime: Casting_Time){
        if(this.castingTime.contains(castingTime)) this.castingTime.remove(castingTime)
    }
    fun clearCastingTime(){
        this.castingTime.clear()
    }
    fun getCastingTime() : List<Casting_Time>{
        return this.castingTime.toList()
    }
    fun addAreaOfEffect(areaOfEffect: Area_Of_Effect) {
        if (!this.aoe.contains(areaOfEffect)) this.aoe.add(areaOfEffect)
    }

    fun removeAreaOfEffect(areaOfEffect: Area_Of_Effect) {
        if (this.aoe.contains(areaOfEffect)) this.aoe.remove(areaOfEffect)
    }

    fun clearAreaOfEffect() {
        this.aoe.clear()
    }

    fun getAreaOfEffect(): List<Area_Of_Effect> {
        return this.aoe.toList()
    }
    fun addDuration(duration: Duration) {
        if (!this.duration.contains(duration)) this.duration.add(duration)
    }

    fun removeDuration(duration: Duration) {
        if (this.duration.contains(duration)) this.duration.remove(duration)
    }

    fun clearDuration() {
        this.duration.clear()
    }

    fun getDuration(): List<Duration> {
        return this.duration.toList()
    }
    fun addComponent(component: Component) {
        if (!this.components.contains(component)) this.components.add(component)
    }

    fun removeComponent(component: Component) {
        if (this.components.contains(component)) this.components.remove(component)
    }

    fun clearComponent() {
        this.components.clear()
    }

    fun getComponent(): List<Component> {
        return this.components.toList()
    }
    fun addDamageType(damageType: Damage_Type) {
        if (!this.damage.contains(damageType)) this.damage.add(damageType)
    }

    fun removeDamageType(damageType: Damage_Type) {
        if (this.damage.contains(damageType)) this.damage.remove(damageType)
    }

    fun clearDamageType() {
        this.damage.clear()
    }

    fun getDamageType(): List<Damage_Type> {
        return this.damage.toList()
    }
    fun addClass(charClass: Classes) {
        if (!this.classes.contains(charClass)) this.classes.add(charClass)
    }

    fun removeClass(charClass: Classes) {
        if (this.classes.contains(charClass)) this.classes.remove(charClass)
    }

    fun clearClasses() {
        this.classes.clear()
    }

    fun getClasses(): List<Classes> {
        return this.classes.toList()
    }
    fun addRitual(isRitual : Boolean){
        if (!this.ritual.contains(isRitual)) this.ritual.add(isRitual)
    }
    fun removeRitual(isRitual : Boolean){
        if (this.ritual.contains(isRitual)) this.ritual.remove(isRitual)
    }
    fun clearRitual() {
        this.ritual.clear()
    }

    fun getIsRitual(): List<Boolean> {
        return this.ritual.toList()
    }
    fun addConcentration(isConcentration : Boolean){
        if (!this.concentration.contains(isConcentration)) this.concentration.add(isConcentration)
    }
    fun removeConcentration(isConcentration : Boolean){
        if (this.concentration.contains(isConcentration)) this.concentration.remove(isConcentration)
    }
    fun clearConcentration() {
        this.concentration.clear()
    }

    fun getIsConcentration(): List<Boolean> {
        return this.concentration.toList()
    }
    fun addLevel(level : Int){
        if (!this.level.contains(level)) this.level.add(level)
    }
    fun removeLevel(level : Int){
        if (this.level.contains(level)) this.level.remove(level)
    }
    fun clearLevel() {
        this.level.clear()
    }

    fun getLevel(): List<Int> {
        return this.level.toList()
    }
    enum class School(val value: String) {
        ABJURATION("Abjuration"),
        CONJURATION("Conjuration"),
        DIVINATION("Divination"),
        ENCHANTMENT("Enchantment"),
        EVOCATION("Evocation"),
        ILLUSION("Illusion"),
        NECROMANCY("Necromancy"),
        TRANSMUTATION("Transmutation")
    }
    enum class Casting_Time(val value: String) {
        INSTANTANEOUS("INSTANTANEOUS"),
        ACTION("1 Action"),
        BONUS_ACTION("1 Bonus Action"),
        REACTION("1 Reaction"),
        MINUTE("1 Minute"),
        MINUTES_10("10 Minutes"),
        HOUR("1 Hour")
    }

    enum class Area_Of_Effect(val value: String) {
        BURST("Burst"),
        CONE("Cone"),
        CYLINDER("Cylinder"),
        LINE("Line"),
        SPHERE("Sphere")
    }

    enum class Duration(val value: String) {
        INSTANTANEOUS("INSTANTANEOUS"),
        ROUND("1 Round"),
        MINUTE("1 Minute"),
        MINUTES_10("10 Minutes"),
        HOUR("1 Hour"),
        HOURS_8("8 Hours"),
        HOURS_24("24 Hours"),
        CONCENTRATION("Concentration"),
        PERMANENT("Permanent")
    }

    enum class Component(val value: String) {
        VERBAL("V"),
        SOMATIC("S"),
        MATERIAL("M")
    }

    enum class Damage_Type(val value: String) {
        ACID("Acid"),
        BLUDGEONING("Bludgeoning"),
        COLD("Cold"),
        FIRE("Fire"),
        FORCE("Force"),
        LIGHTNING("Lightning"),
        NECROTIC("Necrotic"),
        PIERCING("Piercing"),
        POISON("Poison"),
        PSYCHIC("Psychic"),
        RADIANT("Radiant"),
        SLASHING("Slashing"),
        THUNDER("Thunder")
    }

    enum class Classes(val value: String) {
        ARTIFICER("Artificer"),
        BARBARIAN("Barbarian"),
        BARD("Bard"),
        CLERIC("Cleric"),
        DRUID("Druid"),
        FIGHTER("Fighter"),
        MONK("Monk"),
        PALADIN("Paladin"),
        RANGER("Ranger"),
        ROGUE("Rogue"),
        SORCERER("Sorcerer"),
        WARLOCK("Warlock"),
        WIZARD("Wizard")
    }
}