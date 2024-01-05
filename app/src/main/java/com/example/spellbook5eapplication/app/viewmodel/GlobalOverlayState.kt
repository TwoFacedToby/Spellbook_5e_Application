package com.example.spellbook5eapplication.app.viewmodel
import androidx.compose.runtime.mutableStateListOf
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info

object GlobalOverlayState {

    private val overlayStack = mutableStateListOf<OverlayType>()

    var currentSpell: Spell_Info.SpellInfo? = null

    fun showOverlayWithSpell(overlayType: OverlayType, spell: Spell_Info.SpellInfo) {
        currentSpell = spell
        overlayStack.add(overlayType)
    }

    fun showOverlay(overlayType: OverlayType) {
        overlayStack.add(overlayType)
    }


    fun dismissOverlay() {
        if (overlayStack.isNotEmpty()) {
            overlayStack.removeAt(overlayStack.size - 1)
        }
    }

    fun dismissAllOverlays(){
        overlayStack.clear()
    }

    fun getTopOverlay(): OverlayType? {
        return overlayStack.lastOrNull()
    }

    fun getOverlayStack(): List<OverlayType> {
        return overlayStack
    }

    fun isOverlayVisible(overlayType: OverlayType): Boolean {
        return overlayType in overlayStack
    }
}