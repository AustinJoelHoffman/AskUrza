package com.example.askurzaapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planeswalkers")
data class PlaneswalkerEntity(
    @PrimaryKey val scryfallId: String,
    val multiverseId: Int?,
    val name: String,
    val typeLine: String,
    val imageUri: String?,
    val scryfallUri: String
)
