package com.example.spellbook5eapplication.app.Utility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.view.bottomNavigation.Screens
import com.example.spellbook5eapplication.app.view.utilities.UserInputField

sealed class MakePages(
    val route: String
)  {
        object Name: MakePages(
            route = "Name"
        )

        object Level: MakePages(
            route = "Level"
        )

        /*
        companion object {
            fun titleForRoute(route: String?): String {
                return when(route) {
                    Search.route -> Search.title
                    else -> ""
                }
            }
        } */


    /*
    companion object {
        fun titleForRoute(route: String?): String {
            return when(route) {
                Search.route -> Search.title
                else -> ""
            }
        }
    } */
}

