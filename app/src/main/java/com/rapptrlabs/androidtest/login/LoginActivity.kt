package com.rapptrlabs.androidtest.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.rapptrlabs.androidtest.ui.MainActivity
import com.rapptrlabs.androidtest.databinding.ActivityLoginBinding
import com.rapptrlabs.androidtest.viewmodel.LoginViewModel

/**
 * A screen that displays a login prompt, allowing the user to login to the D & A Technologies Web Server.
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        // TODO: Make the UI look like it does in the mock-up. Allow for horizontal screen rotation.
        // TODO: Add a ripple effect when the buttons are clicked
        // TODO: Save screen state on screen rotation, inputted username and password should not disappear on screen rotation

        // TODO: Send 'email' and 'password' to http://dev.rapptrlabs.com/Tests/scripts/login.php as FormUrlEncoded parameters.

        // TODO: When you receive a response from the login endpoint, display an AlertDialog.
        // TODO: The AlertDialog should display the 'code' and 'message' that was returned by the endpoint.
        // TODO: The AlertDialog should also display how long the API call took in milliseconds.
        // TODO: When a login is successful, tapping 'OK' on the AlertDialog should bring us back to the MainActivity

        // TODO: The only valid login credentials are:
        // TODO: email: info@rapptrlabs.com
        // TODO: password: Test123
        // TODO: so please use those to test the login.

        // Observe ViewModel LiveData for login response
        loginViewModel.loginResponseModel.observe(this) { response ->
            showLoginResponseDialog(response.message, response.time)
        }

        // Set listeners for login button
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            loginViewModel.sendLoginRequest(email, password)
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
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun showLoginResponseDialog(message: String, apiCallTime: Long) {
        AlertDialog.Builder(this)
            .setTitle("Login Response")
            .setMessage("Message: $message\nAPI call took: $apiCallTime ms")
            .setPositiveButton("OK") { dialog, _ ->
                if (message.contains("Success")) {
                    MainActivity.start(this)  // Navigate to MainActivity on success
                }
                dialog.dismiss()
            }
            .show()
    }
}