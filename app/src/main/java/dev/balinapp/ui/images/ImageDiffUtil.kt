package dev.balinapp.ui.images

import androidx.recyclerview.widget.DiffUtil
import dev.balinapp.domain.model.image.ImageOutput

class ImageDiffUtilCallback : DiffUtil.ItemCallback<ImageOutput>() {
    override fun areItemsTheSame(oldItem: ImageOutput, newItem: ImageOutput): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageOutput, newItem: ImageOutput): Boolean {
        return oldItem == newItem
    }
}