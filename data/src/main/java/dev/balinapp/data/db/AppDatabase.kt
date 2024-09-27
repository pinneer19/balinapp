package dev.balinapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.balinapp.data.db.dao.CommentDao
import dev.balinapp.data.db.dao.ImageDao
import dev.balinapp.data.db.entity.CommentEntity
import dev.balinapp.data.db.entity.ImageEntity

@Database(version = 1, entities = [ImageEntity::class, CommentEntity::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun commentDao(): CommentDao
}