package com.randomanimals.data.api

import com.randomanimals.data.model.Animal
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("animals/rand/10")
    suspend fun getAnimalsData(): Response<ArrayList<Animal>>
}