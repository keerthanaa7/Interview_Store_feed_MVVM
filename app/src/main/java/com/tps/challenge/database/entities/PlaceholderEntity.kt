package com.tps.challenge.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * A placeholder entity.
 */
@Entity(tableName = "stores")
data class PlaceholderEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val coverImgUrl: String,
    val status: String,
    val deliveryFeeCents: String
)
