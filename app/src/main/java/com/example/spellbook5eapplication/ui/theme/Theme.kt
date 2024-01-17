package com.example.spellbook5eapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkGrey,
    onPrimary = Color.White,
    onPrimaryContainer = LightGrey,
    secondary = Cream,
    onSecondary = Color.Black,
    secondaryContainer = MediumGrey,
    onSecondaryContainer = LightGrey,
    tertiary = OverlayGrey,
    tertiaryContainer = UnselectedButton,
    onTertiaryContainer = SelectedButton,
    onTertiary = Color.White,
    surface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Cream,
    onPrimary = DarkGrey,
    onPrimaryContainer = LightGrey,
    secondary = Cream,
    onSecondary = Color.Black,
    secondaryContainer = LightGrey,
    onSecondaryContainer = MediumGrey,
    tertiary = LightGrey,
    onTertiary = Color.White,
    tertiaryContainer = SelectedButton,
    onTertiaryContainer = UnselectedButton,
    surface = Color.White

)

object ButtonColors {
    val GreenButton = Color(0xFF3C5E30)
    val RedButton = Color(0xFF791811)
    val UnselectedButton = Color(0xFF292929)
    val SelectedButton = Color(0xFF686868)
    val SpellCardButton = Color(0xFF807E7E)

}

val shapes = Shapes(
    extraSmall = RoundedCornerShape(5.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(15.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(30.dp),
)

@Composable
fun SpellbookTheme(
    darkTheme: Boolean, //isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }

    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}