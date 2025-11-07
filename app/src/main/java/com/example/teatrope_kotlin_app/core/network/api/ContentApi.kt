package com.example.teatrope_kotlin_app.core.network.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

data class FuncionDto(
    @SerializedName("id") val id: String,
    @SerializedName("obra_id") val obraId: String?,
    @SerializedName("fecha") val fecha: String?,    // ajusta tipos según Swagger
    @SerializedName("hora") val hora: String?,
    @SerializedName("teatro_id") val teatroId: String?
)
data class ObraDto(
    @SerializedName("id") val id: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("genero") val genero: String?,
    @SerializedName("imagen") val imagen: String?
)
data class PersonaDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("rol") val rol: String?
)
data class TeatroDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String? = null,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("distrito") val distrito: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("imagen_url") val imagenUrl: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
)

data class TeatroCreateRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("direccion") val direccion: String? = null,
    @SerializedName("distrito") val distrito: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("imagen_url") val imagenUrl: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
)

typealias TeatroUpdateRequest = TeatroCreateRequest

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
    suspend fun funcionesPartialUpdate(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<FuncionDto>
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
    suspend fun obrasPartialUpdate(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<ObraDto>
    @DELETE("content/obras/{id}/")
    suspend fun obrasDelete(@Path("id") id: String): Response<Unit>

    // Personas
    @GET("content/personas/")
    suspend fun personasList(): Response<List<PersonaDto>>
    @POST("content/personas/")
    suspend fun personasCreate(@Body body: PersonaDto): Response<PersonaDto>
    @GET("content/personas/{id}/")
    suspend fun personasRead(@Path("id") id: String): Response<PersonaDto>
    @PUT("content/personas/{id}/")
    suspend fun personasUpdate(@Path("id") id: String, @Body body: PersonaDto): Response<PersonaDto>
    @PATCH("content/personas/{id}/")
    suspend fun personasPartialUpdate(@Path("id") id: String, @Body patch: Map<String, Any?>): Response<PersonaDto>
    @DELETE("content/personas/{id}/")
    suspend fun personasDelete(@Path("id") id: String): Response<Unit>

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
