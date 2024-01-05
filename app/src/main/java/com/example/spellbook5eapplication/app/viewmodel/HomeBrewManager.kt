package com.example.spellbook5eapplication.app.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.spellbook5eapplication.app.Model.Data_Model.BrewContent
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell
import com.example.spellbook5eapplication.app.view.Overlays.CreateSpellOverlay2
import com.example.spellbook5eapplication.app.view.Overlays.UserDropOnly
import com.example.spellbook5eapplication.app.view.Overlays.UsersInputOnly


//Works with BrewFactory2 to create homebrews
class HomeBrewManager {

    fun CreateSpell(): @Composable () -> Unit
     {
        val spell = Spell().apply {
            homebrew = true
        }

        return { Naming(spell = spell) }

    }

    fun EditSpell(spell: Spell) : @Composable () -> Unit{

        return { Naming(spell = spell) }

    }



    private fun Naming(spell: Spell): @Composable () -> Unit {
        return {
            CreateSpellOverlay2(
                spell = spell,
                previous = { /*TODO*/ },
                first = true,
                next = { Description(spell) },
                last = false,
                description = "Give your spell a name\n Simply put what your spell will be reffered by",
                userChoise = {
                    UsersInputOnly(
                        input = spell.name,
                        inputChange = {
                            spell.name = it
                            println("Name set to ${spell.name}")
                        },
                        singleLineInput = true,
                    )
                })
        }
    }


    private fun Level(spell: Spell): @Composable () -> Unit {
        val levels = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

        return {
            CreateSpellOverlay2(
                spell = spell,
                previous = { Naming(spell) },
                first = false,
                next = { Description(spell) },
                last = false,
                description = "What level is the spell\n Level explains how powerfull a spell is, higher level more powerfull",
                userChoise = {
                    UserDropOnly(
                        dropdown = levels,
                        dropName = "Levels",
                        dropChange = {
                            spell.level = it.toInt()
                            println("Level set to ${spell.level}")
                        }
                    )
                })
        }
    }

    private fun Description(spell: Spell): @Composable () -> Unit {
        return {
            CreateSpellOverlay2(
                spell = spell,
                previous = { Level(spell) },
                first = false,
                next = { /* TODO */ },
                last = true,
                description = "Explain what the spell does\n What effect does it have, how is it used, anything can be put here",
                userChoise = {
                    UsersInputOnly(
                        input = spell.desc.toString(),
                        inputChange = {
                            spell.desc = listOf(it)
                            println("Name set to ${spell.desc}")
                        },
                        singleLineInput = false,
                    )
                })
        }
    }

}


@Preview
@Composable
fun testMan(){
    val home: HomeBrewManager = HomeBrewManager()

    home.run {
        CreateSpell()
    }
}

enum class BrewParts {
NAME, DESCRIPTION, LEVEL, HIGHLEVEL, COMPONENTS, MATERIAL,
     RITUAL, CONCENTRATION, RANGE, DURATION, CASTTIME, CLASSES,
     ATTACKTYPE, DAMAGE, DC, AOE
}