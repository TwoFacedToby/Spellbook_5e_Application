package com.example.spellbook5eapplication.app.Utility

data class GraphQLRequestBody(
    val query: String,
    val variables: Map<String, Any>? = null
)