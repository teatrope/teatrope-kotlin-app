// core/network/ApiModule.kt
package com.example.teatrope_kotlin_app.core.network

import com.example.teatrope_kotlin_app.core.network.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides @Singleton fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)
    @Provides @Singleton fun provideContentApi(retrofit: Retrofit): ContentApi = retrofit.create(ContentApi::class.java)
    @Provides @Singleton fun provideDiscoveryApi(retrofit: Retrofit): DiscoveryApi = retrofit.create(DiscoveryApi::class.java)
    @Provides @Singleton fun provideNotificationsApi(retrofit: Retrofit): NotificationsApi = retrofit.create(NotificationsApi::class.java)
    @Provides @Singleton fun provideTicketsApi(retrofit: Retrofit): TicketsApi = retrofit.create(TicketsApi::class.java)
}
