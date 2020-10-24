package com.nsnk.testcase.data.model

data class CommentsResponseImgur(
    val status: Int,
    val success: Boolean,
    val data: List<ImgurCommentDataModel>
)