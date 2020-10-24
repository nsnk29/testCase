package com.nsnk.testcase.data.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.di.DaggerImgurViewModelInjector
import com.nsnk.testcase.data.di.NetworkModule
import com.nsnk.testcase.data.model.CommentsResponseImgur
import com.nsnk.testcase.data.observer.ImgurCommentMaybeObserver
import com.nsnk.testcase.data.retrofit.ImgurApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ImgurCommentViewModel : ViewModel() {
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
    val commentsApiLiveData: MutableLiveData<LiveDataResult<CommentsResponseImgur>> =
        MutableLiveData()

    @SuppressLint("DefaultLocale")
    fun loadComments(
        id: String
    ) {
        val apiBaseResponse = imgurApi.getComments(id)
        val maybeObserver = ImgurCommentMaybeObserver(loadingVisibility, commentsApiLiveData)
        apiBaseResponse.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(maybeObserver)
    }

}