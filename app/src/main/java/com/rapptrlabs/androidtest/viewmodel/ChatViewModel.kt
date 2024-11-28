package com.rapptrlabs.androidtest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rapptrlabs.androidtest.model.ChatMessageModel
import com.rapptrlabs.androidtest.api.NetworkClient
import com.rapptrlabs.androidtest.model.ChatLogResponseModel
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    // LiveData to store chat messages
    val chatMessages: MutableLiveData<List<ChatMessageModel>> = MutableLiveData()

    private val networkClient = NetworkClient.create()

    // Fetch chat data using coroutines
    fun fetchChatData() {
        viewModelScope.launch {
            try {
                // Directly get the response from the suspend function
                val response: ChatLogResponseModel = networkClient.getChatLog()

                Log.i("ChatViewModel_fetch",response.toString() )

                // Update LiveData with the response data
                chatMessages.postValue(response.data)


            } catch (e: Exception) {
                // We can here add some error handling logic for probable errors (e.g., network issues, API errors)
                Log.e("ChatViewModel", "Error fetching data: ${e.message}")
            }
        }
    }
}
