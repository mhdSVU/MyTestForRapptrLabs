package com.rapptrlabs.androidtest.api

import com.rapptrlabs.androidtest.model.ChatLogResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NetworkClient {

    @GET("Tests/scripts/chat_log.php")
    suspend fun getChatLog(): ChatLogResponseModel

    companion object {
        private const val BASE_URL = "http://dev.rapptrlabs.com/"
              // Create the Retrofit instance
        fun create(): NetworkClient {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(NetworkClient::class.java)
        }
    }
}



