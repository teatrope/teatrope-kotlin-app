// core/network/api/AuthApi.kt
package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class RegisterRequest(
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)
data class RegisterResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?
)

data class TokenLoginRequest(
    @SerializedName("username") val username: String, // o "email" si tu backend lo pide
    @SerializedName("password") val password: String
)
data class TokenLoginResponse(
    @SerializedName("auth_token") val authToken: String // típico de dj-rest-auth/knox/rest-auth
)

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String?,
    @SerializedName("email") val email: String?
)

interface AuthApi {
    // Registro
    @POST("auth/register/")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    // Login con token (opción 1)
    @POST("auth/token/login/")
    suspend fun tokenLogin(@Body body: TokenLoginRequest): Response<TokenLoginResponse>

    @POST("auth/token/logout/")
    suspend fun tokenLogout(): Response<Unit>

    // CRUD de users
    @GET("auth/users/")
    suspend fun usersList(): Response<List<UserDto>>
    @POST("auth/users/")
    suspend fun usersCreate(@Body body: RegisterRequest): Response<UserDto>
    @GET("auth/users/{id}/")
    suspend fun usersRead(@Path("id") id: String): Response<UserDto>
    @PUT("auth/users/{id}/")
    suspend fun usersUpdate(@Path("id") id: String, @Body body: RegisterRequest): Response<UserDto>
    @PATCH("auth/users/{id}/")
    suspend fun usersPartialUpdate(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<UserDto>
    @DELETE("auth/users/{id}/")
    suspend fun usersDelete(@Path("id") id: String): Response<Unit>

    // Login/Logout alternos (opción 2)
    @POST("auth/users/login/")
    suspend fun usersLogin(@Body body: TokenLoginRequest): Response<TokenLoginResponse>
    @POST("auth/users/logout/")
    suspend fun usersLogout(): Response<Unit>
    @POST("auth/users/register/")
    suspend fun usersRegister(@Body body: RegisterRequest): Response<RegisterResponse>
}
