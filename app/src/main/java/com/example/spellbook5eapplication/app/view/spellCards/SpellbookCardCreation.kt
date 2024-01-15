package com.example.spellbook5eapplication.app.view.spellCards

import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Spellbook

class SpellbookCardCreation(spellbook: Spellbook) {


    var spellbookImageID: Int = R.drawable.spellbook_brown

    init {
        setImage(spellbook)
    }

    private fun setImage(spellbook: Spellbook) {
        when (spellbook.imageIdentifier) {
            "spellbook_diamond" -> spellbookImageID = R.drawable.spellbook_diamond
            "spellbook_brown" -> spellbookImageID = R.drawable.spellbook_brown
            else -> spellbookImageID = R.drawable.spellbook_brown
        }
    }

}