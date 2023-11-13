package com.example.spellbook5eapplication.app.view.spellCards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spellbook5eapplication.R
import com.example.spellbook5eapplication.app.Model.Data_Model.Filter
import com.example.spellbook5eapplication.app.Model.Data_Model.SpellList
import com.example.spellbook5eapplication.app.Model.Data_Model.Spell_Info
import com.example.spellbook5eapplication.app.Utility.SpellController

@Composable
fun SpellQuery(filter : Filter?, spellList: SpellList, onDialogRequest: () -> Unit, onOverlayRequest: () -> Unit) {
    var showing : List<Spell_Info.SpellInfo> = emptyList()
    var pagination = false
    if(filter == null){
        if(spellList.getLoaded() < 5){
            SpellController.loadNextFromSpellList(5, spellList) //Loads the start of the list
            showing = spellList.getSpellInfoList() //Sets it to the currenty loaded
            pagination = true
        }
        else if(spellList.getLoaded() != spellList.getIndexList().size){ //Checks if the entire list is already loaded.
            showing = spellList.getSpellInfoList() //Sets it to the entire list
        }
    }
    else{
        if(spellList.getLoaded() != spellList.getIndexList().size){
            SpellController.loadEntireSpellList(spellList) //Loads entire spelllist to be able to filter through the data
            showing = SpellController.searchSpellListWithFilter(spellList, filter).getSpellInfoList() //Sets currently showing to the whole list, as we already have loaded it.
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        items(showing.size) { spellIndex ->
            SpellCard(
                onDialogRequest = { onDialogRequest },
                onOverlayRequest = { onOverlayRequest },
                spell = showing[spellIndex]
            )
        }
    }

}

