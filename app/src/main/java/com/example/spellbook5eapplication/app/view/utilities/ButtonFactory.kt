package com.example.spellbook5eapplication.app.view.utilities
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.spellbook5eapplication.R

interface Button {
    fun render(): @Composable () -> Unit
    fun onClick()
}

class DeleteButton(private val onClickAction: () -> Unit): Button {
    override fun render(): @Composable () -> Unit = {
        IconButton(onClickAction) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Delete spell from local device",
                tint = colorResource(id = R.color.spellcard_button)
            )
        }
    }

    override fun onClick() {
        onClickAction
    }

}

class CloseButton(private val onClickAction: () -> Unit): Button {
    override fun render(): @Composable () -> Unit = {
        IconButton(onClickAction) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close",
                tint = colorResource(id = R.color.spellcard_button)
            )
        }
    }

    override fun onClick() {
        onClickAction()
    }

}

class FavoritesButton(private val onClickAction: () -> Unit): Button {
    override fun render(): @Composable () -> Unit = {
        IconButton(onClick = onClickAction) {
            Icon(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite button",
                tint = colorResource(id = R.color.spellcard_button),
                modifier = Modifier.size(35.dp)
            )
        }
    }
    override fun onClick() {
        onClickAction()
    }

}

class AddButton(private val onClickAction: () -> Unit) : Button {
    override fun render(): @Composable () -> Unit = {
        IconButton(onClick = onClickAction) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "Add",
                tint = colorResource(id = R.color.spellcard_button),
                modifier = Modifier.size(35.dp)
            )
        }
    }

    override fun onClick() {
        onClickAction()
    }
}


interface ButtonFactory {
    fun createButton(onClickAction: () -> Unit): Button
}

class DeleteButtonFactory : ButtonFactory {
    override fun createButton(onClickAction: () -> Unit): Button {
        return DeleteButton(onClickAction)
    }
}
class CloseButtonFactory : ButtonFactory {
    override fun createButton(onClickAction: () -> Unit): Button {
        return CloseButton(onClickAction)
    }
}

class FavoritesButtonFactory : ButtonFactory {
    override fun createButton(onClickAction: () -> Unit): Button {
        return FavoritesButton(onClickAction)
    }
}

class AddButtonFactory : ButtonFactory {
    override fun createButton(onClickAction: () -> Unit): Button {
        return AddButton(onClickAction)
    }
}