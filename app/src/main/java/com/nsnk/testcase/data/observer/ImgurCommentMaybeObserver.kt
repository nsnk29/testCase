package com.nsnk.testcase.data.observer

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.nsnk.testcase.data.LiveDataResult
import com.nsnk.testcase.data.model.CommentsResponseImgur
import io.reactivex.rxjava3.core.MaybeObserver
import io.reactivex.rxjava3.disposables.Disposable

class ImgurCommentMaybeObserver(
    private val loadingVisibility: MutableLiveData<Int>? = null,
    private val commentApiImgurData: MutableLiveData<LiveDataResult<CommentsResponseImgur>> = MutableLiveData()
) : MaybeObserver<CommentsResponseImgur> {
    override fun onSubscribe(d: Disposable?) {
        loadingVisibility?.value = View.VISIBLE
    }

    override fun onSuccess(t: CommentsResponseImgur) {
        loadingVisibility?.value = View.GONE
        if (t.success) {
            commentApiImgurData.postValue(LiveDataResult.success(t))
        } else {
            onError(Throwable("Imgur API error: ${t.status}"))
        }
    }

    override fun onError(e: Throwable?) {
        loadingVisibility?.value = View.GONE
        commentApiImgurData.postValue(LiveDataResult.error())
    }

    override fun onComplete() {}
}