package com.example.spellbook5eapplication.app.view.viewutilities

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import kotlin.math.max

@Composable
fun KeyboardVisibilityDetector(onKeyboardVisibilityChanged: (Boolean) -> Unit) {
    val context = LocalContext.current
    val activity = context.findActivity()
    val rootView = activity?.window?.decorView?.rootView

    DisposableEffect(rootView) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            rootView?.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView?.height ?: 0
            val keypadHeight = max(0, screenHeight - rect.bottom)

            onKeyboardVisibilityChanged(keypadHeight > screenHeight * 0.15)
        }

        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(listener)
        onDispose {
            rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(listener)
        }
    }
}

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}