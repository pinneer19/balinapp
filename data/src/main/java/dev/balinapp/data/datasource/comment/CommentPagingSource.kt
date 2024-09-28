package dev.balinapp.data.datasource.comment

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.balinapp.data.api.CommentService
import dev.balinapp.data.db.AppDatabase
import dev.balinapp.data.mapper.comment.toCommentOutput
import dev.balinapp.domain.model.comment.CommentOutput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CommentPagingSource @Inject constructor(
    private val db: AppDatabase,
    private val service: CommentService
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getComments(imageId: Int): Flow<PagingData<CommentOutput>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CommentRemoteMediator(imageId, db, service),
            pagingSourceFactory = {
                db.commentDao().commentPagingSource()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toCommentOutput() }
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}