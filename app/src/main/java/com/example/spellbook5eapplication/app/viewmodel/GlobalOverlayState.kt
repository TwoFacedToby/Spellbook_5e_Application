package com.example.spellbook5eapplication.app.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info

class GlobalOverlayState {
    private val overlayStack = mutableStateListOf<OverlayType>()

    var selectedSpellInfo: Spell_Info.SpellInfo? by mutableStateOf(null)

    fun showOverlay(overlayType: OverlayType, spellInfo: Spell_Info.SpellInfo? = null) {
        if (overlayType == OverlayType.LARGE_SPELLCARD) {
            selectedSpellInfo = spellInfo
        }
        overlayStack.add(overlayType)
    }

    fun dismissOverlay() {
        selectedSpellInfo = null
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