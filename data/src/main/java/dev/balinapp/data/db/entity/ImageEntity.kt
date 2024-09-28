package dev.balinapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("url") val url: String,
    @ColumnInfo("date") val date: Long,
    @ColumnInfo("lat") val lat: Double,
    @ColumnInfo("lng") val lng: Double
)