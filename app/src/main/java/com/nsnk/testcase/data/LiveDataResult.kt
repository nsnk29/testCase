package com.nsnk.testcase.data

class LiveDataResult<T>(val status: Status, val response: T? = null) {
    companion object {
        fun <T> success(data: T) = LiveDataResult(Status.SUCCESS, data)
        fun <T> started() = LiveDataResult<T>(Status.STARTED)
        fun <T> error() = LiveDataResult<T>(Status.ERROR, null)
    }

    enum class Status {
        SUCCESS, ERROR, STARTED
    }
}