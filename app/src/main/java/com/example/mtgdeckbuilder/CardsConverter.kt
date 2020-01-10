package com.example.mtgdeckbuilder

import androidx.room.TypeConverter
import com.google.gson.Gson

class CardsConverter {
    @TypeConverter
    fun fromString(value: String?): List<Card>? {
        val objects = Gson().fromJson(value, Array<Card>::class.java) as Array<Card>
        return objects.toList()
    }

    @TypeConverter
    fun fromList(cards: List<Card>?): String? {
        return Gson().toJson(cards)
    }
}