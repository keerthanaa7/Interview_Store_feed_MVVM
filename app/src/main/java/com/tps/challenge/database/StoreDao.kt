package com.tps.challenge.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.tps.challenge.database.entities.PlaceholderEntity

@Dao
interface StoreDao {

    @Query("SELECT * FROM stores")
    suspend fun getAllStores(): List<PlaceholderEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(stores: List<PlaceholderEntity>)

    @Query("DELETE FROM stores")
    suspend fun clearAll()
}