    package com.example.spellbook5eapplication.app.viewmodel

    import SignInViewModel
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
    import com.example.spellbook5eapplication.app.Repository.HomeBrewRepository
    import com.example.spellbook5eapplication.app.Utility.LocalDataLoader
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.example.spellbook5eapplication.app.Utility.GlobalLogInState
    import com.example.spellbook5eapplication.app.view.AuthUI.SignInState
    import com.google.gson.Gson

    class CreateSpellViewModel() : ViewModel() {
        private val repository: HomeBrewRepository = HomeBrewRepository()

        var spell by mutableStateOf(Spell.SpellInfo(
            json = "",
            index = null,
            name = "",
            desc = listOf(),
            atHigherLevel = listOf(),
            range = "",
            components = listOf(),
            materials = "",
            ritual = false,
            duration = "",
            concentration = false,
            casting_time = "",
            level = 0,
            school = Spell.SpellSchool(index = "", name = "", url = "Homebrew"),
            classes = listOf(),
            url = "",
            attackType = "",
            damage = Spell.SpellDamage(Spell.SpellDamageType(name = "", index = "")),
            dc = "",
            homebrew = true
        ))
            private set
        fun updateName(updatedName: String){
            spell.name = updatedName
        }

        fun updateDescription(updateDescription: List<String>){
            spell.desc = updateDescription
        }

        fun updateLevel(updatedLevel: Int){
            spell.level = updatedLevel
        }

        fun updateHigherLevel(updatedHigh: List<String>){
            spell.atHigherLevel = updatedHigh
        }

        fun updateComponents(updatedComp: List<String>){
            spell.components = updatedComp.filterNot { it == "" }
        }

        fun updateMaterial(updatedMaterial: String){
            spell.materials = updatedMaterial
        }

        fun updateRitual(updatedRitual: Boolean){
            spell.ritual = updatedRitual
        }

        fun updateConcentration(updatedConcentra: Boolean){
            spell.concentration = updatedConcentra
        }

        fun updateRange(updatedRange: String){
            spell.range = updatedRange
        }

        fun updateDuration(updatedDuration: String){
            spell.duration = updatedDuration
        }

        fun updateCastTime(updatedCastTime: String){
            spell.casting_time = updatedCastTime
        }

        fun updateSchool(updatedSchool: String){
            val newSchool by mutableStateOf(Spell.SpellSchool(
                index = updatedSchool.lowercase(),
                name = updatedSchool,
                url = null
            ))

            spell.school = newSchool
        }

        fun updateClass(updatedClass: List<String>){

            var newClassesForSpell =
                mutableListOf<Spell.SpellClass>()

            updatedClass.forEach {
                val newClass by mutableStateOf(Spell.SpellClass(
                index = it.lowercase(),
                name = it,
                url = "Homebrew"
            ))
                newClassesForSpell.add(newClass)
            }
            spell.classes = newClassesForSpell

        }

        fun updateAttackType(updatedAttackType: String){
            spell.attackType = updatedAttackType
        }

        fun updateDamage(updatedDamage: String){

            val newDamage = Spell.SpellDamageType(updatedDamage.lowercase(), updatedDamage)

            spell.damage = Spell.SpellDamage(newDamage)
        }

        fun updateDC(updatedDc: String){
            spell.dc = updatedDc
        }

        fun updateEntireSpell(updatedSpell: Spell.SpellInfo) {
            spell = updatedSpell
        }

        fun saveSpell(){

            // Some fix values in homebrew

            spell.homebrew = true

            spell.index = spell.name!!.lowercase()

            spell.url = "Homebrew"

            // Saving the spell localy
            val gson = Gson()

            spell.json = ""

            var jsonSpell = gson.toJson(spell)
            jsonSpell = "{\"data\":{\"spell\":$jsonSpell}}"

            // adding the json to the spell after all else is added if empty (don't want it to grow indefinitely)
            if(spell.json.isNullOrBlank())
                spell.apply{
                json = jsonSpell
            }

            //What ever way the spell may now be saved on the device, might need to be changed.
            LocalDataLoader.saveJson(jsonSpell, spell.index!!, LocalDataLoader.DataType.HOMEBREW)
            println(jsonSpell)
        }

        fun replaceSpell(oldIndex: String){

            // Some fix values in homebrew

            if(oldIndex != null) {

                spell.homebrew = true

                spell.index = spell.name!!.lowercase()

                spell.url = "Homebrew"

                // Saving the spell localy
                val gson = Gson()

                spell.json = ""

                var jsonSpell = gson.toJson(spell)
                jsonSpell = "{\"data\":{\"spell\":$jsonSpell}}"

                // adding the json to the spell after all else is added if empty (don't want it to grow indefinitely)
                if (spell.json.isNullOrBlank())
                    spell.apply {
                        json = jsonSpell
                    }

                //What ever way the spell may now be saved on the device, might need to be changed.
                LocalDataLoader.deleteFile(oldIndex+".json", LocalDataLoader.DataType.HOMEBREW)
                LocalDataLoader.saveJson(
                    jsonSpell,
                    spell.index!!,
                    LocalDataLoader.DataType.HOMEBREW
                )
                println(jsonSpell)
            }
            else{
                println("Index given is null can not replace")
            }
        }

        fun saveSpellToFirebase(){
                repository.saveHomeBrewSpell(GlobalLogInState.userId, spell.name!!, spell.json!!)
        }

        fun loadSpellFromFirebase(spellName: String){
                repository.loadHomeBrewSpell(GlobalLogInState.userId, spellName)
        }

        fun deleteSpellFromFirebase(spellName: String){
                repository.deleteHomeBrewSpell(GlobalLogInState.userId, spellName)
        }

        fun editSpellFromFirebase(spellName: String){
                repository.editHomeBrewSpell(GlobalLogInState.userId, spellName, spell.json!!)
        }



    }