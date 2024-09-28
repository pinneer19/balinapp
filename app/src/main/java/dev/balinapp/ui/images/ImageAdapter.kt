package dev.balinapp.ui.images

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.balinapp.R
import dev.balinapp.databinding.ImageItemBinding
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.util.toDateString

class ImageAdapter(
    private val onItemClick: (Int, String, Long) -> Unit,
    private val onItemLongClick: (Int) -> Unit
) : PagingDataAdapter<ImageOutput, ImageAdapter.ViewHolder>(ImageDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = getItem(position) ?: return
        with(holder.binding) {
            root.setOnClickListener {
                onItemClick(image.id, image.url, image.date)
            }

            root.setOnLongClickListener {
                onItemLongClick(image.id)
                true
            }

            loadImage(imageView, image.url)
            dateView.text = image.date.toDateString()
        }
    }

    private fun loadImage(imageView: ImageView, url: String) {
        val context = imageView.context

        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.error_placeholder)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
}