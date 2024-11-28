package com.rapptrlabs.androidtest.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.rapptrlabs.androidtest.R
import com.rapptrlabs.androidtest.animation.AnimationActivity
import com.rapptrlabs.androidtest.chat.ChatActivity
import com.rapptrlabs.androidtest.databinding.ActivityMainBinding
import com.rapptrlabs.androidtest.login.LoginActivity

/**
 * The main screen that lets you navigate to all other screens in the app.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.title_activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        /**
         * =========================================================================================
         * INSTRUCTIONS
         * =========================================================================================
         *
         * 1. UI must work on Android phones of multiple sizes. Do not worry about Android Tablets.
         *
         * 2. Use this starter project as a base and build upon it. It is ok to remove some of the
         *    provided code if necessary.
         *
         * 3. Read the additional 'TODO' comments throughout the codebase, they will guide you.
         *
         * 3. Please take care of the bug(s) we left for you in the project as well.
         *
         * Thank you and Good luck. -  Rapptr Labs
         * =========================================================================================
         */

        // TODO: Make the UI look like it does in the mock-up
        // TODO: Add a ripple effect when the buttons are clicked

        binding.chatButton.setOnClickListener { ChatActivity.start(this) }

        binding.loginButton.setOnClickListener { LoginActivity.start(this) }

        binding.animationButton.setOnClickListener { AnimationActivity.start(this) }
    }

    // To Handle the Up button (back button) click event
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
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}