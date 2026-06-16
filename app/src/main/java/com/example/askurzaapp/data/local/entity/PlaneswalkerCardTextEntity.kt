package com.example.askurzaapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planeswalker_card_text")
data class PlaneswalkerCardTextEntity(
    @PrimaryKey val scryfallId: String,
    val multiverseId: Int?,
    val cardText: String,
    val cardTextTittle: String,
    val cardTextName: String,
    val cardTextManaCost: String,
    val cardSymbol: String,
    val cardTextTypeLine: String,
    val cardTextBox: String,
    val cardTextOracle: String,
    val cardTextStats: String
)
