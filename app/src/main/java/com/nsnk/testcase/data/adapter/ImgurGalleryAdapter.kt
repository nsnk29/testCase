package com.nsnk.testcase.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nsnk.testcase.R
import com.nsnk.testcase.data.fragment.ImgurListFragment.Companion.columnScreenHeight
import com.nsnk.testcase.data.fragment.ImgurListFragment.Companion.columnScreenWidth
import com.nsnk.testcase.data.model.ImgurImageDataModel
import com.nsnk.testcase.databinding.ItemImgurImageBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject


class ImgurGalleryAdapter : RecyclerView.Adapter<ImgurGalleryAdapter.ImgurImageViewHolder>() {

    val clickSubject: PublishSubject<ImgurImageDataModel> = PublishSubject.create()
    val clickEvent: Observable<ImgurImageDataModel> = clickSubject
    var modelList: MutableList<ImgurImageDataModel> = mutableListOf()


    fun updateList(list: List<ImgurImageDataModel>?) {
        list?.let {
            this.modelList.addAll(list)
            notifyItemRangeInserted(modelList.size - list.size, list.size)
        }
    }

    fun isEmpty(): Boolean = modelList.isNullOrEmpty()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgurImageViewHolder {
        val binding: ItemImgurImageBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_imgur_image,
                parent,
                false
            )
        return ImgurImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImgurImageViewHolder, position: Int) {
        holder.bind(modelList[position])
    }

    override fun getItemCount(): Int = modelList.size

    inner class ImgurImageViewHolder(private val binding: ItemImgurImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardView.setOnClickListener {
                clickSubject.onNext(binding.model)
            }
        }

        fun bind(model: ImgurImageDataModel) {
            binding.model = model
            val imageWidth: Int = model.getImageWidth()
            val imageHeight: Int = model.getImageHeight()
            val realHeight =
                kotlin.math.min(imageHeight * columnScreenWidth / imageWidth, columnScreenHeight)
            binding.imageView.layoutParams =
                LinearLayout.LayoutParams(columnScreenWidth, realHeight)
        }
        // TODO: 24.10.2020 отображение прогресс-бара/сообщения без сети
    }
}