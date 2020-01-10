package com.example.mtgdeckbuilder

class CardRepository {
    private val movieApi: MTGApiService = MTGApi.createApi()

    fun getCards(name: String?, color: String?, type: String?) = movieApi.getCards(name, color, type)
}