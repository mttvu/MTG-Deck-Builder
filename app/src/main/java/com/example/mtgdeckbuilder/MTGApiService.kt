package com.example.mtgdeckbuilder

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MTGApiService {
    // The GET method needed to retrieve a list of cards
    @GET("/v1/cards?contains=imageUrl")
    fun getCards(@Query("name") name: String?,@Query("colors") color: String?,@Query("types") type: String?): Call<CardsResult>
}
