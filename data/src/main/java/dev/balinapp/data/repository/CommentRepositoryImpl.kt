package dev.balinapp.data.repository

import dev.balinapp.data.api.CommentService
import dev.balinapp.data.db.dao.CommentDao
import dev.balinapp.data.mapper.comment.toCommentDtoIn
import dev.balinapp.data.mapper.comment.toCommentEntity
import dev.balinapp.data.mapper.comment.toCommentOutput
import dev.balinapp.data.mapper.toRequestResult
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.comment.CommentInput
import dev.balinapp.domain.model.comment.CommentOutput
import dev.balinapp.domain.model.map
import dev.balinapp.domain.repository.CommentRepository
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentService: CommentService,
    private val commentDao: CommentDao,
) : CommentRepository {
    override suspend fun uploadComment(
        imageId: Int,
        commentInput: CommentInput
    ): RequestResult<CommentOutput> {
        val apiResult = commentService
            .postComment(imageId, commentInput.toCommentDtoIn())
            .toRequestResult()
            .map { it.data.toCommentOutput() }

        if (apiResult is RequestResult.Success) {
            commentDao.insertComment(apiResult.data.toCommentEntity())
        }

        return apiResult
    }

    override suspend fun deleteComment(imageId: Int, commentId: Int): RequestResult<CommentOutput> {
        val apiResult = commentService
            .deleteComment(imageId, commentId)
            .toRequestResult()
            .map { it.data.toCommentOutput() }

        if (apiResult is RequestResult.Success) {
            commentDao.deleteComment(commentDao.getCommentById(commentId))
        }

        return apiResult
    }
}