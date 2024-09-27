package dev.balinapp.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.db.dao.CommentDao
import dev.balinapp.data.db.dao.ImageDao
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = APP_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideImageDao(database: AppDatabase): ImageDao = database.imageDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: AppDatabase): CommentDao = database.commentDao()

    companion object {
        private const val APP_DATABASE = "app_db"
    }
}