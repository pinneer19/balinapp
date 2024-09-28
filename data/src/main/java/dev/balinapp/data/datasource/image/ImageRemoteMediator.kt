package dev.balinapp.data.datasource.image

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.balinapp.data.api.ImageService
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.db.entity.ImageEntity
import dev.balinapp.data.mapper.image.toImageEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator @Inject constructor(
    private val db: AppDatabase,
    private val imageService: ImageService
) : RemoteMediator<Int, ImageEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> null

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    lastItem?.let { it.id / state.config.pageSize } ?: (DEFAULT_PAGE + 1)
                }
            } ?: DEFAULT_PAGE

            val response = imageService.getImages(page = page)
            val imageEntities = response.getOrNull()?.data?.map { dto -> dto.toImageEntity() }

            imageEntities?.let {
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.imageDao().clearAll()
                    }
                    db.imageDao().insertAll(it)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = imageEntities.isNullOrEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 0
    }
}
