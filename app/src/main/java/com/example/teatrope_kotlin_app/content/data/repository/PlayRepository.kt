package com.example.teatrope_kotlin_app.domain.repository

import com.example.teatrope_kotlin_app.core.network.api.ObraWriteRequest
import com.example.teatrope_kotlin_app.domain.model.Play

interface PlayRepository {
    suspend fun getPlays(): List<Play>
    suspend fun createPlay(req: ObraWriteRequest): Play
}
