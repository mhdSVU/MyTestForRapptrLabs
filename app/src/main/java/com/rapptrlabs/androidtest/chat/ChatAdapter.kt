package com.rapptrlabs.androidtest.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.rapptrlabs.androidtest.R
import com.rapptrlabs.androidtest.model.ChatMessageModel
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class ChatAdapter(private var messages: List<ChatMessageModel> = mutableListOf()) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)

        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        Log.d("ChatAdapter", "Avatar URL: ${messages[position].avatar_url}")  // Log the avatar URL
        holder.bind(message)

    }

    override fun getItemCount(): Int = messages.size

    fun updateMessages(newMessages: List<ChatMessageModel>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private var messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private var usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)


        fun bind(message: ChatMessageModel) {
            messageTextView.text = message.message
            usernameTextView.text = message.name
            displayImageWithGlide(message.avatar_url);


        }

        private fun displayImageWithGlide(avatarUrl: String) {
            Glide.with(itemView.context)
                .load(avatarUrl)
                .transform(CircleCrop())  // Apply CircleCrop transformation
                .placeholder(R.drawable.ic_avatar_placeholder)
                .error(R.drawable.ic_avatar_placeholder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Glide", "Failed to load image", e)
                        return false  // Allow Glide to handle the error
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(avatarImageView)

        }
    }
}