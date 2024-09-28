package dev.balinapp.ui.comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.balinapp.databinding.CommentItemBinding
import dev.balinapp.domain.model.comment.CommentOutput
import dev.balinapp.util.toDateStringWithTime

class CommentAdapter(
    private val onItemLongClick: (Int) -> Unit
) : PagingDataAdapter<CommentOutput, CommentAdapter.ViewHolder>(CommentDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = getItem(position) ?: return
        with(holder.binding) {
            commentText.text = comment.text
            commentDate.text = comment.date.toDateStringWithTime()

            root.setOnLongClickListener {
                onItemLongClick(comment.id)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CommentItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    class ViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root)
}