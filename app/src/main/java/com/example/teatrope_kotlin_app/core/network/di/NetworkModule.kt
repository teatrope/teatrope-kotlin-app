
package com.example.teatrope_kotlin_app.core.network.di

import com.example.teatrope_kotlin_app.BuildConfig
import com.example.teatrope_kotlin_app.core.network.AuthTokenProvider
import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import androidx.hilt.navigation.compose.hiltViewModel

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttp(tokenProvider: AuthTokenProvider): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val t = tokenProvider.getToken()
                val req = chain.request().newBuilder()
                    .apply { if (!t.isNullOrBlank()) header("Authorization", "Token $t") }
                    .build()
                chain.proceed(req)
            }
            .build()
    }

    fun provideContentApi(retrofit: Retrofit): ContentApi =
        retrofit.create(ContentApi::class.java)

    @Provides @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL) // Determina el api
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
