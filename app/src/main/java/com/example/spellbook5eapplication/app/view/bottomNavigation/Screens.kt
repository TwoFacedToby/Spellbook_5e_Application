package com.example.spellbook5eapplication.app.view.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String, val title: String,
    val icon: ImageVector
) {
    object Search: Screens(
        route = "search_screen",
        title = "Search",
        icon = Icons.Outlined.Search
    )
    object Favorite: Screens(
        route = "favorite_screen",
        title = "Favorite",
        icon = Icons.Outlined.FavoriteBorder
    )
    object Spellbooks: Screens(
        route = "spell_books_screen",
        title = "Spell books",
        icon = Icons.Outlined.Book
    )
    object Homebrew: Screens(
        route = "homebrew_screen",
        title = "Homebrew",
        icon = Icons.Outlined.Add
    )

    object QuickPlay: Screens(
        route = "quickplay_screen",
        title = "Quick Play",
        icon = Icons.Outlined.PlayArrow
    )

    object Login: Screens(
        route = "login_screen",
        title = "Login",
        icon = Icons.AutoMirrored.Outlined.MenuBook

    )

    object CreateAccount: Screens(
        route = "create_account_screen",
        title = "Create Account",
        icon = Icons.AutoMirrored.Outlined.List
    )

    companion object {
        fun titleForRoute(route: String?): String {
            return when(route) {
                Search.route -> Search.title
                Favorite.route -> Favorite.title
                Spellbooks.route -> Spellbooks.title
                Homebrew.route -> Homebrew.title
                QuickPlay.route -> QuickPlay.title
                Login.route -> Login.title
                else -> ""
            }
        }
    }
}
