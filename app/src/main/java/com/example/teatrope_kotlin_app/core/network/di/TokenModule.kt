package com.example.teatrope_kotlin_app.core.network.di

import android.content.Context
import com.example.teatrope_kotlin_app.core.network.AuthTokenProvider
import com.example.teatrope_kotlin_app.core.network.StaticTokenProvider
import com.example.teatrope_kotlin_app.core.network.session.TokenStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {


    @Binds
    @Singleton
    abstract fun bindAuthTokenProvider(impl: StaticTokenProvider): AuthTokenProvider

    companion object {
        @Provides
        @Singleton
        fun provideTokenStorage(@ApplicationContext ctx: Context): TokenStorage =
            TokenStorage(ctx)
    }
}
