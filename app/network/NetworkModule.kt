@Provides @Singleton
fun provideAuthInterceptor(tokenProvider: AuthTokenProvider): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val token = tokenProvider.getToken()
    val req = if (!token.isNullOrBlank()) {
        original.newBuilder()
            .addHeader("Authorization", "Token $token") // Cambia a "Bearer $token" si usas JWT
            .build()
    } else original
    chain.proceed(req)
}