package com.nsnk.testcase.data

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nsnk.testcase.data.ImgurViewModel.Companion.DEFAULT_PAGE
import com.nsnk.testcase.data.model.BaseResponseImgur
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable

class ImgurMaybeObserver(
    private val loadingVisibility: MutableLiveData<Int>? = null,
    private val apiImgurData: MutableLiveData<LiveDataResult<BaseResponseImgur>> = MutableLiveData(),
    private val page: Int = 0
) : MaybeObserver<BaseResponseImgur> {
    override fun onSubscribe(d: Disposable?) {
        if (page == DEFAULT_PAGE) {
            loadingVisibility?.value = View.VISIBLE
        }
        apiImgurData.postValue(LiveDataResult.started())
    }

    override fun onSuccess(t: BaseResponseImgur) {
        loadingVisibility?.value = View.GONE
        if (t.success) {
            apiImgurData.postValue(LiveDataResult.success(t))
        } else {
            onError(Throwable("Imgur API error: ${t.status}"))
        }
    }

    override fun onError(e: Throwable?) {
        loadingVisibility?.value = View.GONE
        apiImgurData.postValue(LiveDataResult.error())
    }

    override fun onComplete() {}
}