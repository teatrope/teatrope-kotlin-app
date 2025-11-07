package com.example.teatrope_kotlin_app.core.network.di

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)

            .respectCacheHeaders(false)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .logger(DebugLogger())
            .build()
    }
}
