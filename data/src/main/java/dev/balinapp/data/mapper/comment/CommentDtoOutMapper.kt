package dev.balinapp.data.mapper.comment

import dev.balinapp.data.model.comment.CommentDtoOut
import dev.balinapp.domain.model.comment.CommentOutput

fun CommentDtoOut?.toCommentOutput(): CommentOutput {
    return this?.run {
        CommentOutput(
            id = id ?: -1,
            date = date ?: -1,
            text = text.orEmpty()
        )
    } ?: CommentOutput(
        id = -1,
        date = -1,
        text = ""
    )
}