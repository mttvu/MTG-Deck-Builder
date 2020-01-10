package com.example.mtgdeckbuilder

import com.google.gson.annotations.SerializedName

data class CardsResult(
    @SerializedName("cards") var cards: List<Card>

)