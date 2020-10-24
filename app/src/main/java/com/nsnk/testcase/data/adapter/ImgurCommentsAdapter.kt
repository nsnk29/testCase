package com.nsnk.testcase.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nsnk.testcase.R
import com.nsnk.testcase.data.model.ImgurCommentDataModel
import com.nsnk.testcase.databinding.ItemImgurCommentBinding

class ImgurCommentsAdapter : RecyclerView.Adapter<ImgurCommentsAdapter.ImgurCommentViewHolder>() {

    var modelList: MutableList<ImgurCommentDataModel> = mutableListOf()

    fun updateList(list: List<ImgurCommentDataModel>?) {
        list?.let {
            this.modelList.addAll(list)
            notifyItemRangeInserted(modelList.size - list.size, list.size)
        }
    }

    fun isEmpty(): Boolean = modelList.isNullOrEmpty()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurCommentViewHolder {
        val binding: ItemImgurCommentBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_imgur_comment,
                parent,
                false
            )
        return ImgurCommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImgurCommentViewHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int = modelList.size

    inner class ImgurCommentViewHolder(private val binding: ItemImgurCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ImgurCommentDataModel) {
            binding.model = model
        }
    }
}