package com.rapptrlabs.androidtest.viewmodel



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rapptrlabs.androidtest.model.LoginResponseModel
import kotlinx.coroutines.*
import okhttp3.*

class LoginViewModel : ViewModel() {

    private val _loginResponseModel = MutableLiveData<LoginResponseModel>()
    val loginResponseModel: LiveData<LoginResponseModel> get() = _loginResponseModel

    private val client = OkHttpClient()

    // Coroutine scope for managing background work
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun sendLoginRequest(email: String, password: String) {
        coroutineScope.launch {
            val startTime = System.currentTimeMillis()
            val formBody = FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url("https://dev.rapptrlabs.com/Tests/scripts/login.php")
                .post(formBody)
                .build()

            try {
                // Perform network request on IO thread
                val response = withContext(Dispatchers.IO) {
                    client.newCall(request).execute()
                }

                // Read response and calculate the API call time
                val responseMessage = response.body?.string() ?: "No Response"
                val timeTaken = System.currentTimeMillis() - startTime

                Log.e("LoginViewModel", "Login responseMessage $responseMessage")

                // Post the response back to the UI thread
                _loginResponseModel.postValue(LoginResponseModel(responseMessage, timeTaken))

            } catch (e: Exception) {
                // We can here add some error handling logic for probable errors (e.g., network issues, API errors)
                Log.e("LoginViewModel", "Login failed", e)
                _loginResponseModel.postValue(LoginResponseModel("Error: ${e.localizedMessage}", System.currentTimeMillis() - startTime))
            }
        }
    }


}