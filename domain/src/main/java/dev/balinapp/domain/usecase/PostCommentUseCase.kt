package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.comment.CommentInput
import dev.balinapp.domain.model.comment.CommentOutput
import dev.balinapp.domain.repository.CommentRepository
import javax.inject.Inject

class PostCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {
    suspend operator fun invoke(imageId: Int, text: String): RequestResult<CommentOutput> {
        return commentRepository.uploadComment(imageId, commentInput = CommentInput(text))
    }
}