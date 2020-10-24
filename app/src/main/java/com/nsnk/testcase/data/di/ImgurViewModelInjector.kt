package com.nsnk.testcase.data.di

import com.nsnk.testcase.data.viewmodel.ImgurCommentViewModel
import com.nsnk.testcase.data.viewmodel.ImgurGalleryViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ImgurViewModelInjector {

    fun inject(galleryViewModel: ImgurGalleryViewModel)
    fun inject(commentViewModel: ImgurCommentViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ImgurViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}