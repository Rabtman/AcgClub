package com.rabtman.common.di.component

import android.app.Application
import com.google.gson.Gson
import com.rabtman.common.base.CommonApplicationLike
import com.rabtman.common.di.module.AppModule
import com.rabtman.common.di.module.ClientModule
import com.rabtman.common.di.module.GlobeConfigModule
import com.rabtman.common.integration.AppManager
import com.rabtman.common.integration.IRepositoryManager
import dagger.Component
import okhttp3.OkHttpClient
import java.io.File
import java.util.*
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, GlobeConfigModule::class, ClientModule::class])
interface AppComponent {
    fun Application(): Application

    //用于管理网络请求层,以及数据缓存层
    fun repositoryManager(): IRepositoryManager

    fun okHttpClient(): OkHttpClient

    //gson
    fun gson(): Gson

    //缓存文件根目录(RxCache和Glide的的缓存都已经作为子文件夹在这个目录里),应该将所有缓存放到这个根目录里,便于管理和清理,可在GlobeConfigModule里配置
    fun cacheFile(): File

    //用于管理所有activity
    fun appManager(): AppManager

    fun statusBarAttr(): HashMap<String, Int>

    fun inject(delegate: CommonApplicationLike)
}