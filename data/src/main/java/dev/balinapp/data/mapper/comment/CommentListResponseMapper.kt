package dev.balinapp.data.mapper.comment

import dev.balinapp.data.db.entity.CommentEntity
import dev.balinapp.data.model.comment.CommentDtoOut

fun CommentDtoOut.toCommentEntity(): CommentEntity = with(this) {
    return CommentEntity(
        id = id ?: -1,
        date = date ?: -1,
        text = text.orEmpty()
    )
}