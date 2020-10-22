package com.nsnk.testcase

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class ImgurPostViewHolder(view: View, private val glide: GlideRequests) :
    RecyclerView.ViewHolder(view) {
    private val image: ImageView = view.findViewById(R.id.imageView)
    private val postTitle: TextView = view.findViewById(R.id.title_text_view)

    init {
        // TODO: 22.10.2020 clicklistener
    }

    fun bind(imageModel: ImageModel) {
        with(imageModel) {
            postTitle.text = title
            glide.load(url)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .into(image)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): ImgurPostViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.imgur_image_item, parent, false)
            return ImgurPostViewHolder(view, glide)
        }
    }
}