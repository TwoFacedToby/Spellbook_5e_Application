package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook

class SpellbookCardCreation(spellbook: Spellbook) {

    var spellbookImageID = when (spellbook.imageIdentifier) {
        "spellbook_diamond" -> R.drawable.spellbook_diamond
        "spellbook_brown" -> R.drawable.spellbook_brown
        else -> R.drawable.spellbook_brown // Just a default image
    }

}