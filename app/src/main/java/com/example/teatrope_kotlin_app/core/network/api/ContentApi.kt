package com.example.teatrope_kotlin_app.core.network.api

import com.example.teatrope_kotlin_app.content.presentation.theaters.ObraUi
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

// Search Result DTO
data class BusquedaResultDto(
    @SerializedName("obras") val obras: List<ObraDto>,
    @SerializedName("teatros") val teatros: List<TeatroDto>
)

// Corrected mapper function
fun ObraDto.toUi(): ObraUi = ObraUi(
    id = id,
    titulo = titulo,
    genero = genero,
    imageUrl = imageUrl.orEmpty(),
    teatroNombre = teatro.nombre,
    buyUrl = buyUrl.orEmpty(),
    director = directorNombre.orEmpty(),

    distrito = teatro.distrito ?: ""
)

// ---------- DTOs ----------
data class FuncionDto(
    @SerializedName("id") val id: String,
    @SerializedName("obra") val obra: ObraDto, // Corregido para anidar ObraDto
    @SerializedName("fecha") val fecha: String?,
    @SerializedName("hora") val hora: String?,
    @SerializedName("disponibilidad") val disponibilidad: String?,
    @SerializedName("buy_url") val buyUrl: String?
)


data class TeatroDto(
    @SerializedName("id") val id: String,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("calle") val calle: String? = null,        //
    @SerializedName("distrito") val distrito: String? = null,
    @SerializedName("latitud") val latitud: Double? = null,
    @SerializedName("longitud") val longitud: Double? = null,
    @SerializedName("image_url") val imageUrl: String? = null  //
)


data class ObraDto(
    @SerializedName("id") val id: String,
    @SerializedName("teatro") val teatro: TeatroDto,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("director_nombre") val directorNombre: String? = null,
    @SerializedName("director_rol") val directorRol: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("buy_url") val buyUrl: String? = null
)


data class ObraWriteRequest(
    @SerializedName("teatro") val teatroId: String,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("genero") val genero: String,
    @SerializedName("director_nombre") val directorNombre: String? = null,
    @SerializedName("director_rol") val directorRol: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("buy_url") val buyUrl: String? = null
)

data class PersonaDto(
    @SerializedName("id") val id: String,
    @SerializedName("obra") val obra: ObraDto, // Corregido para anidar ObraDto
    @SerializedName("nombre_completo") val nombreCompleto: String,
    @SerializedName("rol") val rol: String,
    @SerializedName("image_url") val imageUrl: String? = null
)


/** Requests Teatro */
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

    // --- Busquedas ---
    @GET("discovery/busquedas/")
    suspend fun buscar(@Query("q") query: String): Response<BusquedaResultDto>

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
    suspend fun obrasCreate(@Body body: ObraWriteRequest): Response<ObraDto>

    @GET("content/obras/{id}/")
    suspend fun obrasRead(@Path("id") id: String): Response<ObraDto>

    @PUT("content/obras/{id}/")
    suspend fun obrasUpdate(@Path("id") id: String, @Body body: ObraWriteRequest): Response<ObraDto>

    @PATCH("content/obras/{id}/")
    suspend fun obrasPartialUpdate(
        @Path("id") id: String,
        @Body patch: Map<String, @JvmSuppressWildcards Any?>
    ): Response<ObraDto>

    @DELETE("content/obras/{id}/")
    suspend fun obrasDelete(@Path("id") id: String): Response<Unit>
    
    // Personas
    @GET("content/personas/")
    suspend fun personasList(): Response<List<PersonaDto>>

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
