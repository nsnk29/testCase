package com.nsnk.testcase.data.observer

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.model.GalleryResponseImgur
import com.nsnk.testcase.data.viewmodel.ImgurGalleryViewModel.Companion.DEFAULT_PAGE
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable

class ImgurImageMaybeObserver(
    private val loadingVisibility: MutableLiveData<Int>? = null,
    private val imageApiImgurData: MutableLiveData<LiveDataResult<GalleryResponseImgur>> = MutableLiveData(),
    private val page: Int = 0
) : MaybeObserver<GalleryResponseImgur> {
    override fun onSubscribe(d: Disposable?) {
        if (page == DEFAULT_PAGE) {
            loadingVisibility?.value = View.VISIBLE
        }
    }

    override fun onSuccess(t: GalleryResponseImgur) {
        loadingVisibility?.value = View.GONE

        if (t.success) {
            val filteredJpeg = t.copy(data = t.data.filter { it.isJpegImage() })
            imageApiImgurData.postValue(LiveDataResult.success(filteredJpeg))
        } else {
            onError(Throwable("Imgur API error: ${t.status}"))
        }
    }

    override fun onError(e: Throwable?) {
        loadingVisibility?.value = View.GONE
        imageApiImgurData.postValue(LiveDataResult.error())
    }

    override fun onComplete() {}
}