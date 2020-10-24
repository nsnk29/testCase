package com.nsnk.testcase.data.retrofit

import com.nsnk.testcase.data.model.CommentsResponseImgur
import com.nsnk.testcase.data.model.GalleryResponseImgur
import io.reactivex.rxjava3.core.Maybe
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurApi {
    @GET("/3/gallery/{section}/{sort}/{window}/{page}")
    fun getGallery(
        @Path("section") section: String,
        @Path("sort") sort: String,
        @Path("window") window: String,
        @Path("page") page: Int,
        @Query("showViral") showViral: Boolean = false,
        @Query("mature") mature: Boolean = false,
        @Query("album_previews") albumPreviews: Boolean = false
    ): Maybe<GalleryResponseImgur>

    @GET("/3/gallery/{id}/comments/best")
    fun getComments(
        @Path("id") imageId: String
    ): Maybe<CommentsResponseImgur>
}