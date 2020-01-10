package com.example.mtgdeckbuilder

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity( tableName = "deckTable")
data class Deck(


    @ColumnInfo( name = "name")
    var name: String,

    @ColumnInfo( name = "format")
    var format: String,

    @ColumnInfo( name = "description")
    var description: String,

    @ColumnInfo( name = "cards")
    var cards: MutableList<Card>,

    @ColumnInfo( name = "id")
    @PrimaryKey( autoGenerate = true)
    var id: Long? = null
) : Parcelable