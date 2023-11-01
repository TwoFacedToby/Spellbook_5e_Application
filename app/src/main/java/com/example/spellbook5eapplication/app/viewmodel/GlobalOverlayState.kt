package com.example.spellbook5eapplication.app.viewmodel
import androidx.compose.runtime.mutableStateListOf

class GlobalOverlayState {
    private val overlayStack = mutableStateListOf<OverlayType>()


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