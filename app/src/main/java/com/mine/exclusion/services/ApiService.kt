package com.mine.exclusion.services

import com.mine.exclusion.model.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://my-json-server.typicode.com/"

interface APIService {
    @GET("iranjith4/ad-assignment/db")
    suspend fun getRooms(): Response

    companion object {
        private lateinit var apiService: APIService
        fun getInstance(): APIService {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIService::class.java)
            return apiService
        }
    }
}