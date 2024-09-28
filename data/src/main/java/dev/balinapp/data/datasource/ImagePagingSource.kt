package dev.balinapp.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.balinapp.data.api.ImageService
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.mapper.image.toImageOutput
import dev.balinapp.domain.model.image.ImageOutput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ImagePagingSource @Inject constructor(
    private val db: AppDatabase,
    private val service: ImageService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getImages(): Flow<PagingData<ImageOutput>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = ImageRemoteMediator(db, service),
            pagingSourceFactory = {
                db.imageDao().imagePagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toImageOutput() }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}