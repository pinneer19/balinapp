package dev.balinapp.domain.repository

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.comment.CommentInput
import dev.balinapp.domain.model.comment.CommentOutput

interface CommentRepository {

    suspend fun uploadComment(
        imageId: Int,
        commentInput: CommentInput
    ): RequestResult<CommentOutput>

    suspend fun deleteComment(imageId: Int, commentId: Int): RequestResult<CommentOutput>
}