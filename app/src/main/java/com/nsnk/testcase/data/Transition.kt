package com.nsnk.testcase.data

import android.widget.ImageView
import android.widget.TextView
import com.nsnk.testcase.data.model.ImgurImageDataModel

data class Transition(
    val model: ImgurImageDataModel? = null,
    val imageUrl: String,
    val title: String,
    val imageTransition: ImageView,
    val titleTransition: TextView? = null
)