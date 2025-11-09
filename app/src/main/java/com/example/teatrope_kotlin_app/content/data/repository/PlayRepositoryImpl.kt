package com.example.teatrope_kotlin_app.content.data.repository

import com.example.teatrope_kotlin_app.core.network.api.ContentApi
import com.example.teatrope_kotlin_app.core.network.api.ObraWriteRequest
import com.example.teatrope_kotlin_app.content.data.mapper.toDomain
import com.example.teatrope_kotlin_app.domain.model.Play
import com.example.teatrope_kotlin_app.domain.repository.PlayRepository
import javax.inject.Inject

class PlayRepositoryImpl @Inject constructor (
    private val api: ContentApi
) : PlayRepository {

    override suspend fun getPlays(): List<Play> {
        val resp = api.obrasList()
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return resp.body()?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun createPlay(req: ObraWriteRequest): Play {
        val resp = api.obrasCreate(req)
        if (!resp.isSuccessful) error("HTTP ${resp.code()}")
        return requireNotNull(resp.body()).toDomain()
    }
}
