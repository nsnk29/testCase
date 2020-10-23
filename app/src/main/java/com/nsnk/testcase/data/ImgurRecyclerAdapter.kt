package com.nsnk.testcase.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nsnk.testcase.R
import com.nsnk.testcase.data.model.ImgurImageDataModel
import com.nsnk.testcase.databinding.ItemImgurImageBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject


class ImgurRecyclerAdapter : RecyclerView.Adapter<ImgurRecyclerAdapter.ImgurImageViewHolder>() {

    var subscribe: Disposable? = null
    val clickSubject = PublishSubject.create<Transition>()
    val clickEvent: Observable<Transition> = clickSubject
    var modelList: MutableList<ImgurImageDataModel> = mutableListOf()

    fun setModelListForAdapter(modelList: List<ImgurImageDataModel>) {
        this.modelList = modelList.toMutableList()
    }

    fun addItems(modelList: MutableList<ImgurImageDataModel>) {
        this.modelList.addAll(modelList)
    }

    private fun setList(list: List<ImgurImageDataModel>?) {
        if (list != null) {
            this.modelList = list.toMutableList()
        } else {
            this.modelList.clear()
        }
        notifyDataSetChanged()
    }

    fun updateList(list: List<ImgurImageDataModel>?) {
        list?.let {
            this.modelList.addAll(list)
            notifyItemRangeInserted(modelList.size - list.size, list.size)
        }
    }

    fun isEmpty(): Boolean {
        return modelList.isNullOrEmpty()
    }

    fun clear() {
        setList(emptyList())
    }


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

    inner class ImgurImageViewHolder(val binding: ItemImgurImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // TODO: 23.10.2020 clicklistener
        }

        fun bind(model: ImgurImageDataModel) {
            binding.model = model

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.itemlayout)
            model.apply {
                constraintSet.setDimensionRatio(
                    R.id.imageView,
                    "1:${if (isAlbum) coverHeight / coverWidth else height / width}"
                )
            }
            constraintSet.applyTo(binding.itemlayout)
        }
    }
}