package com.example.spellbook5eapplication.app.view.viewutilities

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BorderedText(text: String) {
    Canvas(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        drawTextWithBorder(text, Offset(100f, 100f))
    }
}

fun DrawScope.drawTextWithBorder(text: String, position: Offset) {
    val paint = Paint().apply {
        color = Color.Black.toArgb()
        textSize = 38.sp.toPx()
        strokeWidth = 8f
        style = android.graphics.Paint.Style.STROKE
    }

    drawContext.canvas.nativeCanvas.drawText(text, position.x, position.y, paint)

    paint.apply {
        color = Color.White.toArgb()
        style = android.graphics.Paint.Style.FILL
    }

    drawContext.canvas.nativeCanvas.drawText(text, position.x, position.y, paint)
}
