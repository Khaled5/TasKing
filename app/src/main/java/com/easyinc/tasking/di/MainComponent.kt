package com.easyinc.tasking.di

import android.app.Application
import com.easyinc.tasking.App
import com.easyinc.tasking.di.module.AppModule
import com.easyinc.tasking.di.module.FragmentBuildersModule
import com.easyinc.tasking.di.module.ThreadingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    FragmentBuildersModule::class,
    ThreadingModule::class
])
interface MainComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application): Builder


        fun build(): MainComponent

    }

}