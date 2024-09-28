package dev.balinapp.data.mapper.comment

import dev.balinapp.data.db.entity.CommentEntity
import dev.balinapp.domain.model.comment.CommentOutput

fun CommentOutput.toCommentEntity(): CommentEntity = with(this) {
    CommentEntity(
        id = id,
        date = date,
        text = text
    )
}