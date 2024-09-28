package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.comment.CommentOutput
import dev.balinapp.domain.repository.CommentRepository
import javax.inject.Inject

class DeleteCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(imageId: Int, commentId: Int): RequestResult<CommentOutput> {
        return commentRepository.deleteComment(imageId, commentId)
    }
}