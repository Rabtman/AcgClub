package com.rabtman.common.di.module

import android.app.Application
import com.google.gson.Gson
import com.rabtman.common.integration.IRepositoryManager
import com.rabtman.common.integration.RepositoryManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mApplication: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideRepositoryManager(repositoryManager: RepositoryManager): IRepositoryManager {
        return repositoryManager
    }
}