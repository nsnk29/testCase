package com.nsnk.testcase.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImgurImageDataModel(
    @SerializedName("account_id")
    val accountId: Int,
    @SerializedName("account_url")
    val accountUrl: String,
    @SerializedName("comment_count")
    val commentCount: Int,
    val cover: String?,
    @SerializedName("cover_height")
    val coverHeight: Int?,
    @SerializedName("cover_width")
    val coverWidth: Int?,
    val width: Int?,
    val height: Int?,
    val datetime: Int,
    val downs: Int,
    val id: String,
    val images: List<Image>?,
    @SerializedName("images_count")
    val imagesCount: Int?,
    @SerializedName("is_album")
    val isAlbum: Boolean,
    val link: String,
    val points: Int,
    val score: Int,
    val section: String?,
    val title: String?,
    val topic: String?,
    val ups: Int,
    val views: Int,
    val type: String?
) : Parcelable {

    fun getImageLink(): String? =
        if (isAlbum && !images.isNullOrEmpty()) {
            "https://i.imgur.com/${cover}.jpg"
        } else "https://i.imgur.com/${id}.jpg"

    fun getImageThumbnail(): String =
        if (isAlbum && !images.isNullOrEmpty()) {
            "https://i.imgur.com/${cover}$THUMBNAIL_SUFFIX.jpg"
        } else
            "https://i.imgur.com/${id}$THUMBNAIL_SUFFIX.jpg"


    fun isJpegImage(): Boolean = (isAlbum && images?.first()?.type == JPEG) || (type == JPEG)
    fun getImageWidth(): Int = if (isAlbum) coverWidth!! else width!!
    fun getImageHeight(): Int = if (isAlbum) coverHeight!! else height!!


    // остальные поля не нужны
    @Parcelize
    data class Image(
        val type: String
    ) : Parcelable

    companion object {
        const val JPEG = "image/jpeg"
        const val THUMBNAIL_SUFFIX = "l"
    }
}