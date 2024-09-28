package dev.balinapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.balinapp.data.db.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comments: List<CommentEntity>)

    @Insert
    suspend fun insertComment(comment: CommentEntity)

    @Query("SELECT * FROM comments")
    fun commentPagingSource(): PagingSource<Int, CommentEntity>

    @Delete
    suspend fun deleteComment(commentEntity: CommentEntity)

    @Query("DELETE FROM comments")
    suspend fun clearAll()

    @Query("SELECT * FROM comments WHERE id = :id")
    suspend fun getCommentById(id: Int): CommentEntity
}