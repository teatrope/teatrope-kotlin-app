package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/* --------- DTOs --------- */

data class RegisterRequest(
    @SerializedName("username") val username: String? = null,
    @SerializedName("email")    val email: String,
    @SerializedName("password") val password: String
)

data class RegisterResponse(
    @SerializedName("id")       val id: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email")    val email: String? = null
)

data class TokenLoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class TokenLoginResponse(

    @SerializedName(value = "auth_token", alternate = ["token"])
    val token: String = "",

    @SerializedName("user")
    val user: UserDto? = null
)

data class UserDto(
    @SerializedName("id")       val id: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email")    val email: String? = null
)

/* --------- API --------- */

interface AuthApi {

    // ---- Registro / Token Auth ----
    @POST("auth/register/")
    suspend fun register(@Body body: RegisterRequest): Response<RegisterResponse>

    @POST("auth/token/login/")
    suspend fun tokenLogin(@Body body: TokenLoginRequest): Response<TokenLoginResponse>

    @POST("auth/token/logout/")
    suspend fun tokenLogout(): Response<Unit>

    // ---- Users  ----
    @GET("auth/users/")
    suspend fun usersList(): Response<List<UserDto>>

    @POST("auth/users/")
    suspend fun usersCreate(@Body body: RegisterRequest): Response<UserDto>

    @GET("auth/users/{id}/")
    suspend fun usersRead(@Path("id") id: String): Response<UserDto>

    @PUT("auth/users/{id}/")
    suspend fun usersUpdate(
        @Path("id") id: String,
        @Body body: RegisterRequest
    ): Response<UserDto>

    @PATCH("auth/users/{id}/")
    suspend fun usersPartialUpdate(
        @Path("id") id: String,
        @Body patch: Map<String, @JvmSuppressWildcards Any?>
    ): Response<UserDto>

    @DELETE("auth/users/{id}/")
    suspend fun usersDelete(@Path("id") id: String): Response<Unit>

    // ---- Endpoints  ----
    @POST("auth/users/login/")
    suspend fun usersLogin(@Body body: TokenLoginRequest): Response<TokenLoginResponse>

    @POST("auth/users/logout/")
    suspend fun usersLogout(): Response<Unit>

    @POST("auth/users/register/")
    suspend fun usersRegister(@Body body: RegisterRequest): Response<RegisterResponse>
}
