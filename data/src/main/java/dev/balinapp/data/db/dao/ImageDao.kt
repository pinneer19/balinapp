package dev.balinapp.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.balinapp.data.db.entity.ImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM images")
    fun getPagedPhotos(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun getImageById(id: Int): ImageEntity

    @Insert
    suspend fun insertAll(imageEntityList: List<ImageEntity>)

    @Insert
    suspend fun insertImage(imageEntity: ImageEntity)

    @Query("SELECT * FROM images")
    fun imagePagingSource(): PagingSource<Int, ImageEntity>

    @Delete
    suspend fun deleteImage(imageEntity: ImageEntity)

    @Query("DELETE FROM images")
    suspend fun clearAll()
}