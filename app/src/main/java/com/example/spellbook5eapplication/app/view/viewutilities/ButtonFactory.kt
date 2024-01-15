package com.example.spellbook5eapplication.app.view.viewutilities

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

interface ButtonFactory {
    fun createAddButton(onClickAction: () -> Unit): @Composable () -> Unit
    fun createDeleteButton(onClickAction: () -> Unit): @Composable () -> Unit
    fun createFavoriteButton(onClickAction: () -> Unit): @Composable () -> Unit
    fun createCloseButton(onClickAction: () -> Unit): @Composable () -> Unit
}

class StandardButtonFactory : ButtonFactory {

    override fun createAddButton(
        onClickAction: () -> Unit
    ): @Composable () -> Unit = {
        IconButton(onClick = onClickAction) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add"
            )
        }
    }

    override fun createDeleteButton(
        onClickAction: () -> Unit
    ): @Composable () -> Unit = {
        IconButton(onClick = onClickAction) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete"
            )
        }
    }

    override fun createFavoriteButton(
        onClickAction: () -> Unit
    ): @Composable () -> Unit = {
        IconButton(onClick = onClickAction) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite"
            )
        }
    }

    override fun createCloseButton(onClickAction: () -> Unit): @Composable () -> Unit {
        TODO("Not yet implemented")
    }
}
