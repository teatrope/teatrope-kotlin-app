package com.example.teatrope_kotlin_app.core.di

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.content.data.repository.PlayRepositoryImpl
import com.example.teatrope_kotlin_app.domain.repository.PlayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ContentModule {

    @Provides @Singleton
    fun providePlayRepository(api: ContentApi): PlayRepository =
        PlayRepositoryImpl(api)
}
