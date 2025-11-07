package com.example.teatrope_kotlin_app.core.ui

import com.example.teatrope_kotlin_app.core.network.api.TeatroDto

val TeatroDto.thumbUrl: String?
    get() = try {

        val f1 = this::class.members.firstOrNull { it.name == "image_url" }?.call(this) as? String
        val f2 = this::class.members.firstOrNull { it.name == "imageUrl" }?.call(this) as? String
        f1 ?: f2
    } catch (_: Exception) { null }
