package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class FadeSide {
    LEFT, RIGHT, BOTTOM, TOP
}

fun Modifier.fadingEdge(
    side: FadeSide,
    color: Color,
    width: Dp,
    isVisible: Boolean,
    shape: Shape? = null,
    spec: AnimationSpec<Dp>? = null
): Modifier = composed {

    val animatedWidth = spec?.let {
        animateDpAsState(
            targetValue = if (isVisible) width else 0.dp,
            animationSpec = spec,
            label = "Fade width"
        ).value
    }

    val baseModifier = if (shape != null) this.then(clip(shape)) else this

    baseModifier.drawWithContent {
        drawContent()

        val (start, end) = when (side) {
            FadeSide.LEFT -> Offset.Zero to Offset(size.width, 0f)
            FadeSide.RIGHT -> Offset(size.width, 0f) to Offset.Zero
            FadeSide.BOTTOM -> Offset(0f, size.height) to Offset.Zero
            FadeSide.TOP -> Offset.Zero to Offset(0f, size.height)
        }

        val staticWidth = if (isVisible) width.toPx() else 0f

        val widthPx = animatedWidth?.toPx() ?: staticWidth

        val fraction: Float = when (side) {
            FadeSide.LEFT, FadeSide.RIGHT -> widthPx / size.width
            FadeSide.BOTTOM, FadeSide.TOP -> widthPx / size.height
        }

        drawRect(
            brush = Brush.linearGradient(
                0f to color,
                fraction to Color.Transparent,
                start = start,
                end = end
            ),
            size = size
        )
    }
}