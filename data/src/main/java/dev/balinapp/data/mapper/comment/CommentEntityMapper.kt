package dev.balinapp.data.mapper.comment

import dev.balinapp.data.db.entity.CommentEntity
import dev.balinapp.domain.model.comment.CommentOutput

fun CommentEntity.toCommentOutput(): CommentOutput = with(this) {
    CommentOutput(
        id = id,
        date = date,
        text = text
    )
}