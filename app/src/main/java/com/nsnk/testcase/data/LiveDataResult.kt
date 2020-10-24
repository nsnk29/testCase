package com.nsnk.testcase.data

// класс-обёртка для удобной обработки результатов запросов к API
class LiveDataResult<T>(val status: Status, val response: T? = null) {
    companion object {
        fun <T> success(data: T) = LiveDataResult(Status.SUCCESS, data)
        fun <T> error() = LiveDataResult<T>(Status.ERROR, null)
    }

    enum class Status {
        SUCCESS, ERROR
    }
}