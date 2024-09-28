package dev.balinapp.ui.comments

import androidx.recyclerview.widget.DiffUtil
import dev.balinapp.domain.model.comment.CommentOutput

class CommentDiffUtilCallback : DiffUtil.ItemCallback<CommentOutput>() {
    override fun areItemsTheSame(oldItem: CommentOutput, newItem: CommentOutput): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CommentOutput, newItem: CommentOutput): Boolean {
        return oldItem == newItem
    }
}