package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// ---------- DTOs ----------

data class FuncionDto(
    @SerializedName("id") val id: String,
    @SerializedName("obra_id") val obraId: String?,
    @SerializedName("fecha") val fecha: String?,   // ajusta a LocalDate si luego usas adaptador
    @SerializedName("hora") val hora: String?,
    @SerializedName("teatro_id") val teatroId: String?
)

data class ObraDto(
    @SerializedName("id") val id: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("genero") val genero: String? = null,
    @SerializedName("imagen") val imagen: String? = null
)

data class PersonaDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("rol") val rol: String? = null
)


data class TeatroDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("calle") val calle: String? = null,        // <- antes "direccion"
    @SerializedName("distrito") val distrito: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("image_url") val imageUrl: String? = null  // <- antes "imagen_url"
)

/** Requests para crear/actualizar (misma forma que el backend espera) */
data class TeatroCreateRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("calle") val calle: String? = null,
    @SerializedName("distrito") val distrito: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("image_url") val imageUrl: String? = null
)

typealias TeatroUpdateRequest = TeatroCreateRequest

// ---------- API ----------

interface ContentApi {

    // Funciones
    @GET("content/funciones/")
    suspend fun funcionesList(): Response<List<FuncionDto>>

    @POST("content/funciones/")
    suspend fun funcionesCreate(@Body body: FuncionDto): Response<FuncionDto>

    @GET("content/funciones/{id}/")
    suspend fun funcionesRead(@Path("id") id: String): Response<FuncionDto>

    @PUT("content/funciones/{id}/")
    suspend fun funcionesUpdate(@Path("id") id: String, @Body body: FuncionDto): Response<FuncionDto>

    @PATCH("content/funciones/{id}/")
    suspend fun funcionesPartialUpdate(
        @Path("id") id: String,
        @Body patch: Map<String, @JvmSuppressWildcards Any?>
    ): Response<FuncionDto>

    @DELETE("content/funciones/{id}/")
    suspend fun funcionesDelete(@Path("id") id: String): Response<Unit>

    // Obras
    @GET("content/obras/")
    suspend fun obrasList(): Response<List<ObraDto>>

    @POST("content/obras/")
    suspend fun obrasCreate(@Body body: ObraDto): Response<ObraDto>

    @GET("content/obras/{id}/")
    suspend fun obrasRead(@Path("id") id: String): Response<ObraDto>

    @PUT("content/obras/{id}/")
    suspend fun obrasUpdate(@Path("id") id: String, @Body body: ObraDto): Response<ObraDto>

    @PATCH("content/obras/{id}/")
    suspend fun obrasPartialUpdate(
        @Path("id") id: String,
        @Body patch: Map<String, @JvmSuppressWildcards Any?>
    ): Response<ObraDto>

    @DELETE("content/obras/{id}/")
    suspend fun obrasDelete(@Path("id") id: String): Response<Unit>

    // Teatros
    @GET("content/teatros/")
    suspend fun getTeatros(): Response<List<TeatroDto>>

    @POST("content/teatros/")
    suspend fun createTeatro(@Body body: TeatroCreateRequest): Response<TeatroDto>

    @GET("content/teatros/{id}/")
    suspend fun getTeatro(@Path("id") id: String): Response<TeatroDto>

    @PUT("content/teatros/{id}/")
    suspend fun updateTeatro(
        @Path("id") id: String,
        @Body body: TeatroUpdateRequest
    ): Response<TeatroDto>

    @PATCH("content/teatros/{id}/")
    suspend fun patchTeatro(
        @Path("id") id: String,
        @Body patch: Map<String, @JvmSuppressWildcards Any?>
    ): Response<TeatroDto>

    @DELETE("content/teatros/{id}/")
    suspend fun deleteTeatro(@Path("id") id: String): Response<Unit>
}
