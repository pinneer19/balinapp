package dev.balinapp.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("date") val date: Long,
    @ColumnInfo("text") val text: String
)