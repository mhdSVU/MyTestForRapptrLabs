package com.rapptrlabs.androidtest.model

data class ChatMessageModel(
    //Note: I found some bugs here, we have always to pay attention to any probable mismatch between fields names and JSON attributes in the API response
    val user_id : Int,
    val avatar_url : String,
    val name : String,
    val message: String
)