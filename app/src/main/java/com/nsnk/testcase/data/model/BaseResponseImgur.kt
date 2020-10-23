package com.nsnk.testcase.data.model

data class BaseResponseImgur(
    val status: Int,
    val success: Boolean,
    val data: List<ImgurImageDataModel>
)