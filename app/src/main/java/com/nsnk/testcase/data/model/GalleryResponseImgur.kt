package com.nsnk.testcase.data.model

data class GalleryResponseImgur(
    val status: Int,
    val success: Boolean,
    val data: List<ImgurImageDataModel>
)