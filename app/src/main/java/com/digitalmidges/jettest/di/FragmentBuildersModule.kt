package com.digitalmidges.jettest.di

import com.digitalmidges.jettest.ui.fragments.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

}
