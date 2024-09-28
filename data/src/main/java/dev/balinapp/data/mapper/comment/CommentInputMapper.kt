package dev.balinapp.data.mapper.comment

import dev.balinapp.data.model.comment.CommentDtoIn
import dev.balinapp.domain.model.comment.CommentInput

fun CommentInput.toCommentDtoIn(): CommentDtoIn =
    CommentDtoIn(
        text = this.text
    )