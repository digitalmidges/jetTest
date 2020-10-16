package com.digitalmidges.jettest

import android.app.Application
import android.content.Context
import com.digitalmidges.jettest.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.ref.WeakReference
import javax.inject.Inject

class BaseApplication : Application(), HasAndroidInjector {


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    companion object {
        lateinit var instance: BaseApplication
            private set

        lateinit var context: WeakReference<Context>
            private set

        fun getContext(): Context {
            return context.get()!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = WeakReference(this)
        AppInjector.init(this) // The Dagger magic! init the dependencies injection

    }

}