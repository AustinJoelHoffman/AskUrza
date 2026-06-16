package com.example.askurzaapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loyalty_abilities")
data class LoyaltyAbilitiesEntity(
    @PrimaryKey val scryfallId: String,
    val multiverseId: Int?,
    val otherAbility: String?,
    val firstLoyaltyAbilityCost: String?,
    val firstLoyaltyAbility: String?,
    val secondLoyaltyAbilityCost: String?,
    val secondLoyaltyAbility: String?,
    val thirdLoyaltyAbilityCost: String?,
    val thirdLoyaltyAbility: String?,
    val fourthLoyaltyAbilityCost: String?,
    val fourthLoyaltyAbility: String?,
    val startingLoyalty: String?
)
