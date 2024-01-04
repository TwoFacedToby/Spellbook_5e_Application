package com.example.spellbook5eapplication.app.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell

object GlobalOverlayState {
    private val overlayStack = mutableStateListOf<OverlayType>()
    private var currentOverlaySpell by mutableStateOf<Spell?>(null)



    fun setOverlaySpell(spell: Spell) {
        currentOverlaySpell = spell
    }

    fun getOverlaySpell(): Spell? {
        return currentOverlaySpell
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

    fun removeSpecificOverlay(overlayType: OverlayType) {
        if(overlayStack.contains(overlayType)){
            overlayStack.remove(overlayType)
        }
    }

    fun getOverlayStack(): List<OverlayType> {
        return overlayStack
    }

    fun isOverlayVisible(overlayType: OverlayType): Boolean {
        return overlayType in overlayStack
    }
}