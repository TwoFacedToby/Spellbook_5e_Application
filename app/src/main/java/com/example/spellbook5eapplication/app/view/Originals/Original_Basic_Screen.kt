package com.example.spellbook5eapplication.app.view.Originals

/*@Composable
fun Basic_Screen(globalOverlayState: GlobalOverlayState,
                 spellsLiveData: LiveData<List<Spell_Info.SpellInfo?>>,
                 enablePagination: Boolean,
                 customContent: @Composable (() -> Unit)? = null){
    val spellList : SpellList?
    runBlocking {
        spellList = SpellController.getAllSpellsList()
    }

    var filter by remember { mutableStateOf(Filter())}

    println("Current filter: $filter")
    println("Current filter level size: " + filter.getLevel().size)

    val nullSpell = Spell_Info.SpellInfo(null, "Example name", null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null , null, null, null, null , null, null, null, null , null, null, null, null , null, null)
    var overlaySpell by remember { mutableStateOf(nullSpell) }



    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.search_view_background),
                contentDescription = "Search view background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
                alpha = 0.5F
            )
            Column (modifier = Modifier.padding(top = 100.dp, bottom = 56.dp).matchParentSize()) {
                // TopBar with Search and Filters
                SearchFilterBar(globalOverlayState)
                // List of Spells, taking up all available space
                Box(modifier = Modifier.fillMaxHeight().weight(3f)){
                    SpellQuery(
                        filter = filter,
                        spellsLiveData = spellsLiveData,
                        onFullSpellCardRequest = {
                            overlaySpell = it
                            globalOverlayState.showOverlay(OverlayType.LARGE_SPELLCARD)
                        },
                        onAddToSpellbookRequest = {
                            overlaySpell = it
                            globalOverlayState.showOverlay(OverlayType.ADD_TO_SPELLBOOK)
                        },
                        enablePagination = enablePagination
                    )
                }

                if (customContent != null) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth().weight(0.5f))
                    {
                        customContent()
                    }
                }
            }



            for (overlayType in globalOverlayState.getOverlayStack()) {
                when (overlayType) {
                    OverlayType.LARGE_SPELLCARD -> {
                        LargeSpellCardOverlay(globalOverlayState, { globalOverlayState.dismissOverlay() }, overlaySpell)
                    }
                    OverlayType.ADD_TO_SPELLBOOK -> {
                        CustomOverlay(
                            globalOverlayState = globalOverlayState,
                            overlayType = OverlayType.ADD_TO_SPELLBOOK,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ) {
                            AddToSpellBookOverlay(
                                spellInfo = overlaySpell,
                                onDismissRequest = { globalOverlayState.dismissOverlay() }
                            )
                        }
                    }
                    OverlayType.FILTER -> {
                        CustomOverlay(
                            globalOverlayState = globalOverlayState,
                            overlayType = OverlayType.FILTER,
                            onDismissRequest = { globalOverlayState.dismissOverlay() }
                        ){
                            FiltersOverlay(
                                onDismissRequest = { globalOverlayState.dismissOverlay() },
                                currentfilter = filter,
                                createNewFilter = { Filter() },
                                updateFilterState = { newFilter ->
                                    filter = newFilter
                                    println("Filter updated: $filter") }
                            )
                        }
                    }
                    else -> Unit
                }

            }

        }

    }
}

@Composable
fun SearchFilterBar(globalOverlayState: GlobalOverlayState){
    var filter by remember { mutableStateOf(Filter())}

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    )
    {
        UserInputField(
            label = "Search",
            singleLine = true,
            onInputChanged = {
                    input ->
                filter = updateFilterWithSearchName(filter, input)
                println("User input: $input")
            },
            modifier = Modifier.size(height = 24.dp, width =  120.dp),
            imeAction = ImeAction.Search
        )
        Spacer(modifier = Modifier.width(5.dp))
        FilterButton(
            onShowFiltersRequest = {
                globalOverlayState.showOverlay(
                    OverlayType.FILTER,
                )
            })
    }
}*/