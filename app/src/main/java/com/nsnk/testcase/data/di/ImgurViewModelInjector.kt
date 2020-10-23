package com.nsnk.testcase.data.di

import com.nsnk.testcase.data.ImgurViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ImgurViewModelInjector {

    fun inject(viewModel: ImgurViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ImgurViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
    }
}