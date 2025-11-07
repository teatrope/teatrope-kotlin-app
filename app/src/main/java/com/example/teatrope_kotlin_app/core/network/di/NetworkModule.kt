package com.example.teatrope_kotlin_app.core.network.di

import com.example.teatrope_kotlin_app.BuildConfig
import com.example.teatrope_kotlin_app.core.network.AuthTokenProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {



    @Provides @Singleton
    fun provideOkHttp(tokenProvider: AuthTokenProvider): OkHttpClient {
        val logging = okhttp3.logging.HttpLoggingInterceptor().apply {
            level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging) //
            .addInterceptor { chain ->
                val t = tokenProvider.getToken()
                val req = chain.request().newBuilder()
                    .apply { if (!t.isNullOrBlank()) header("Authorization", "Bearer $t") }
                    .build()
                chain.proceed(req)
            }
            .build()
    }


    @Provides @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL) // https://teatrope-api.../api/
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
