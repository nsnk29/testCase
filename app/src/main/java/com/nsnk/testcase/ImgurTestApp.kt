package com.nsnk.testcase

import android.app.Application
import android.content.res.Resources
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ImgurTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // очистка кэша
        GlobalScope.launch {
            Glide.get(applicationContext)
                .clearDiskCache()
        }

        app = this
        res = resources
    }

    companion object {
        lateinit var app: ImgurTestApp
        lateinit var res: Resources
    }
}