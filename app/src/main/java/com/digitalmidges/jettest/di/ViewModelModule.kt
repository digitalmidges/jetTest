package com.digitalmidges.jettest.di


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalmidges.jettest.viewModels.HomeViewModel
import com.digitalmidges.jettest.viewModels.MainViewModel
import com.digitalmidges.jettest.viewModels.MainViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: MainViewModelFactory): ViewModelProvider.Factory

}
