package dev.balinapp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.Module
import dagger.Provides
import dev.balinapp.data.BuildConfig
import dev.balinapp.data.api.ImageService
import dev.balinapp.data.datasource.ImageRemoteMediator
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.db.entity.ImageEntity
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(BuildConfig.DATASTORE_FILE) }
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideImagePager(db: AppDatabase, service: ImageService): Pager<Int, ImageEntity> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = ImageRemoteMediator(db, service),
            pagingSourceFactory = {
                db.imageDao().imagePagingSource()
            }
        )
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}