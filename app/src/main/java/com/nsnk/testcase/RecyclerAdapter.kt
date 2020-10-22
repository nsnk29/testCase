package com.nsnk.testcase

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val items: ArrayList<ImageModel>, private val glide: GlideRequests) :
    RecyclerView.Adapter<ImgurPostViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurPostViewHolder =
        ImgurPostViewHolder.create(parent, glide)

    override fun onBindViewHolder(holder: ImgurPostViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

}