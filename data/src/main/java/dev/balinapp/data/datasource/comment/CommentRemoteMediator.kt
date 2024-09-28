package dev.balinapp.data.datasource.comment

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.balinapp.data.api.CommentService
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.db.entity.CommentEntity
import dev.balinapp.data.mapper.comment.toCommentEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CommentRemoteMediator @Inject constructor(
    private val imageId: Int,
    private val db: AppDatabase,
    private val commentService: CommentService
) : RemoteMediator<Int, CommentEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CommentEntity>
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

            val response = commentService.getComments(imageId = imageId, page = page)

            val commentEntities = response.getOrNull()?.data?.map { dto -> dto.toCommentEntity() }
            commentEntities?.let {
                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.commentDao().clearAll()
                    }
                    db.commentDao().insertAll(it)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = commentEntities.isNullOrEmpty()
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
