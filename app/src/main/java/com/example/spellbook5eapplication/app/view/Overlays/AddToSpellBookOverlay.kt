package com.example.spellbook5eapplication.app.view.Overlays

/*
@Composable
fun AddToSpellBookOverlay(
    onDismissRequest: () -> Unit,
    spellInfo: Spell.SpellInfo
) {
    //Initializing viewModel to make the app recompose when a new spellbook is selected.

    TODO Make a new ViewModel that uses LiveData
    val viewModel: SpellbookViewModel = viewModel(
        factory = SpellbookViewModelFactory(SpellController, SpelllistLoader)
    )


    val context = LocalContext.current
    //val spellbooks = viewModel.spellbooks
    var showDialog by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 15.dp, end = 15.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(250.dp)
                .height(15.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .clickable { onDismissRequest() },
            color = colorResource(id = R.color.black).copy(alpha = 0.2F),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Add to spellbook",
            color = colorResource(id = R.color.white),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))


        OverlayBox(
            content = {
                if (spellbooks != null) {
                    items(spellbooks) { string ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = string.spellbookName,
                                modifier = Modifier.padding(15.dp),
                                color = colorResource(id = R.color.white)
                            )
                            IconButton(
                                onClick = { // Replace 'YourTag' with an appropriate tag for your log messages
                                    println("Spellbook clicked: ${string.spellbookName}")
                                    val chosenSpellBook = SpellbookManager.getSpellbook(string.spellbookName)
                                    if (chosenSpellBook != null) {
                                        var wasAdded = SpellbookManager.getSpellbook(chosenSpellBook.spellbookName)?.spells?.add(spellInfo.index!!)


                                        if(wasAdded!!){
                                            SpellbookManager.saveSpellbookToFile(chosenSpellBook.spellbookName)
                                            Toast.makeText(context, "Added to ${chosenSpellBook.spellbookName}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    println("Added spell ${spellInfo.index} to spellbook ${string.spellbookName}")
                                }

                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Add,
                                    contentDescription = "Add to spellbook",
                                    tint = colorResource(id = R.color.spellcard_button),
                                    modifier = Modifier.size(35.dp)
                                )
                            }
                        }
                    }
                } else {
                    items(1) {
                        Text(
                            text = "You have not created any spellbooks yet",
                            fontWeight = FontWeight.Bold,
                            color = colorResource
                                (id = R.color.white),
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ColouredButton(
            label = "Create new spellbook",
            modifier = Modifier,
            color = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.green_button)),
            onClick = { showDialog = true})
        if (showDialog) {
            CreateDialog(onDismissRequest = { showDialog = false })

        }
    }
}

/*
@Preview
@Composable
fun SpellBookOverlayPreview(){
    AddToSpellBookOverlay(onDismissRequest = {
        println("Dismiss button clicked")
    })
}

 */
*/
