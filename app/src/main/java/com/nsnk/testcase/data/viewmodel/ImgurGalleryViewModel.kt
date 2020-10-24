package com.nsnk.testcase.data.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.di.DaggerImgurViewModelInjector
import com.nsnk.testcase.data.di.NetworkModule
import com.nsnk.testcase.data.model.GalleryResponseImgur
import com.nsnk.testcase.data.observer.ImgurImageMaybeObserver
import com.nsnk.testcase.data.retrofit.ImgurApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ImgurGalleryViewModel : ViewModel() {

    init {
        inject()
    }

    private fun inject() {
        DaggerImgurViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build().inject(this)
    }

    @Inject
    lateinit var imgurApi: ImgurApi
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val galleryApiLiveData: MutableLiveData<LiveDataResult<GalleryResponseImgur>> =
        MutableLiveData()

    private var subscription: Disposable? = null

    @SuppressLint("DefaultLocale")
    fun loadImages(
        section: String = DEFAULT_SECTION,
        sort: String = DEFAULT_SORT,
        window: String = DEFAULT_WINDOW,
        page: Int = DEFAULT_PAGE,
    ) {
        val apiBaseResponse = imgurApi.getGallery(section, sort, window, page)
        val maybeObserver = ImgurImageMaybeObserver(loadingVisibility, galleryApiLiveData, page)
        apiBaseResponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(maybeObserver)
    }


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

    companion object {
        const val DEFAULT_SECTION = "hot"
        const val DEFAULT_SORT = "top"
        const val DEFAULT_WINDOW = "week"
        const val DEFAULT_PAGE = 0
    }
}