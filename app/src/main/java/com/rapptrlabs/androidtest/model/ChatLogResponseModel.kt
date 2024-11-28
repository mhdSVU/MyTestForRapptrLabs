package com.rapptrlabs.androidtest.model

// This is the wrapper for the entire response
data class ChatLogResponseModel(
    val data: List<ChatMessageModel> // This contains the actual list of messages
)