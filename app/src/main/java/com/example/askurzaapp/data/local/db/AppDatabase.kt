package com.example.askurzaapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.askurzaapp.data.local.dao.LoyaltyAbilityDao
import com.example.askurzaapp.data.local.entity.LoyaltyAbilitiesEntity
import com.example.askurzaapp.data.local.entity.PlaneswalkerCardTextEntity
import com.example.askurzaapp.data.local.entity.PlaneswalkerEntity

@Database(
    entities = [
        PlaneswalkerEntity::class,
        PlaneswalkerCardTextEntity::class,
        LoyaltyAbilitiesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loyaltyAbilityDao(): LoyaltyAbilityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "planeswalker_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
