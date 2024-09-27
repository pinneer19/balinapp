package dev.balinapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.balinapp.data.db.entity.CommentEntity

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments")
    suspend fun getCommentsFromDb(): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Delete
    suspend fun deleteComment(commentEntity: CommentEntity)
}