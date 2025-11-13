package com.example.teatrope_kotlin_app.core.di

import com.example.teatrope_kotlin_app.content.data.repository.PlayRepository
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepositoryImpl
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepository
import com.example.teatrope_kotlin_app.content.data.repository.TheaterRepositoryImpl
import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentModule {

    @Provides
    @Singleton
    fun providePlayRepository(api: ContentApi): PlayRepository = PlayRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideTheaterRepository(api: ContentApi): TheaterRepository = TheaterRepositoryImpl(api)
}
