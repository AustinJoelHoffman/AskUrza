package com.example.askurzaapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.askurzaapp.data.local.entity.LoyaltyAbilitiesEntity
import com.example.askurzaapp.data.local.entity.PlaneswalkerCardTextEntity
import com.example.askurzaapp.data.local.entity.PlaneswalkerEntity

@Dao
interface LoyaltyAbilityDao {
    @Query("SELECT * FROM loyalty_abilities ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomLoyaltyAbility(): LoyaltyAbilitiesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLoyalty(loyalty: List<LoyaltyAbilitiesEntity>)

    @Query("DELETE FROM loyalty_abilities")
    suspend fun clearLoyalty()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaneswalkers(planeswalkers: List<PlaneswalkerEntity>)

    @Query("DELETE FROM planeswalkers")
    suspend fun clearPlaneswalkers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardText(cardTexts: List<PlaneswalkerCardTextEntity>)

    @Query("DELETE FROM planeswalker_card_text")
    suspend fun clearCardText()
}
