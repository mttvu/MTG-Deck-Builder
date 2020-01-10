package com.example.mtgdeckbuilder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Card(
    var name: String,
    var manaCost: String?,
    var cmc: Int?,
    var colors: List<String>,
    var type: String,
    var types: List<String>,
    var rarity: String,
    var text: String,
    var imageUrl: String
) : Parcelable {
}