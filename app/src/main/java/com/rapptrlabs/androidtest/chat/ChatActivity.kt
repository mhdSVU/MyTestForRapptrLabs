package com.rapptrlabs.androidtest.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rapptrlabs.androidtest.ui.MainActivity
import com.rapptrlabs.androidtest.model.ChatMessageModel
import com.rapptrlabs.androidtest.databinding.ActivityChatBinding
import com.rapptrlabs.androidtest.viewmodel.ChatViewModel

/**
 * Screen that displays a list of chats from a chat log.
 */
class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatAdapter: ChatAdapter
    private val chatViewModel: ChatViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        chatAdapter = ChatAdapter()


        binding.recyclerView.apply {
            adapter = chatAdapter
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
        }

        // Observe the chat messages from the ViewModel
        chatViewModel.chatMessages.observe(this) { messages ->
            chatAdapter.updateMessages(messages)
        }

        // Fetch chat data when the activity is created
        try {
            chatViewModel.fetchChatData()

        } catch (e: Exception) {
            e.printStackTrace()  // Log the error (or handle it appropriately)
            Log.e("ChatActivity", "Error in fetchChatData() ${e.message.toString()}")

            null // Return null or an error message to signal the failure
        }


        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.

        // TODO: Retrieve the chat data from http://dev.rapptrlabs.com/Tests/scripts/chat_log.php
        // TODO: Parse this chat data from JSON into ChatLogMessageModel and display it.
    }


    //We might use this later for better UX
    private fun showError(exception: Exception) {
        // Show a Toast with the error message to the user
        Toast.makeText(this, "Failed to load chat data: ${exception.message}", Toast.LENGTH_LONG)
            .show()

        // Optionally log the error details for debugging purposes
        Log.e("ChatActivity", "Error fetching chat data", exception)
    }

//We might use this later for better UX
    private fun showLoading(isLoading: Boolean) {
        // Optionally, show or hide a loading spinner here, if implemented
        if (isLoading) {
            // Show loading spinner (you can implement this in your layout if needed)
            binding.progressBar.visibility = android.view.View.VISIBLE
        } else {
            // Hide the loading spinner once data is loaded
            binding.progressBar.visibility = android.view.View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ChatActivity::class.java)
            context.startActivity(intent)
        }
    }
}