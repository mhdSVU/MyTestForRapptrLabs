package com.rapptrlabs.androidtest.animation

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.rapptrlabs.androidtest.R
import com.rapptrlabs.androidtest.ui.MainActivity
import com.rapptrlabs.androidtest.databinding.ActivityAnimationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Screen that displays the D & A Technologies logo.
 * The icon can be moved around on the screen as well as animated.
 */
class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding
    private var xDelta = 0f
    private var yDelta = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val logoImageView = binding.logoImageView
        val fadeButton: MaterialButton = binding.fadeButton

        // Set up the fade button click listener to trigger the animation
        fadeButton.setOnClickListener {
            animateImageView(logoImageView)
        // We have added some fun bonuses: Sound, more Animation, Color change and explosion
            funBonus(logoImageView)
        }

        // Implement drag functionality for the ImageView
        logoImageView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Remember the initial touch position
                    xDelta = event.rawX - v.x
                    yDelta = event.rawY - v.y
                }

                MotionEvent.ACTION_MOVE -> {
                    // Move the ImageView based on touch
                    v.animate()
                        .x(event.rawX - xDelta)
                        .y(event.rawY - yDelta)
                        .setDuration(0)
                        .start()
                }
            }
            true
        }
    }

    private fun animateImageView(view: View) {
        // Fade out: from 100% alpha to 0% alpha
        val fadeOut = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        fadeOut.duration = 1000 // Duration of the fade-out (1 second)

        // Fade in: from 0% alpha to 100% alpha
        val fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        fadeIn.duration = 1000 // Duration of the fade-in (1 second)

        // Play the fade-out animation followed by the fade-in animation
        fadeOut.doOnEnd {
            fadeIn.start()
        }

        // Start the fade-out animation first
        fadeOut.start()
    }


    private fun funBonus(logoImageView: ImageView) {
        val delay: Long = 3000 // 3 seconds

        // Change the background color of the layout with animation
        val colorAnimator = ObjectAnimator.ofArgb(
            binding.root,
            "backgroundColor",
            Color.WHITE, // Initial color
            Color.parseColor("#7B7B7E"), // New color
            Color.WHITE // Reset to initial color
        )
        colorAnimator.duration = 2000 // 2 seconds

        // Play a rain and thundering sound
        val mediaPlayer = MediaPlayer.create(this, R.raw.rain_thundering)
        mediaPlayer.start()

        // Stop the sound of the rain and thundering after 3 seconds

        CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            mediaPlayer.release()
        }

        // Add a simple scale animation for the logo
        val scaleX = ObjectAnimator.ofFloat(logoImageView, "scaleX", 1f, 1.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(logoImageView, "scaleY", 1f, 1.5f, 1f)
        scaleX.duration = 1000
        scaleY.duration = 1000

        CoroutineScope(Dispatchers.Main).launch {
            //Wait 3 seconds before starting a new animation
            delay(delay)
            // Start the animations
            colorAnimator.start()
            scaleX.start()
            scaleY.start()
        }


        // Show a Toast message
        Toast.makeText(this, "Explosion!!! 💥💥💥", Toast.LENGTH_SHORT).show()

        // Release media player when the sound finishes
        mediaPlayer.setOnCompletionListener {
            mediaPlayer.release()
        }
    }


    // Handle the Up button (back button) click event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle the back navigation (you can finish the activity here or navigate up)
                onBackPressed() // or NavUtils.navigateUpFromSameTask(this)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AnimationActivity::class.java)
            context.startActivity(intent)
        }
    }
}