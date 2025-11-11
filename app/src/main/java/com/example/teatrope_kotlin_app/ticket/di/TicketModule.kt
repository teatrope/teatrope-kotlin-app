package com.example.teatrope_kotlin_app.ticket.di

import com.example.teatrope_kotlin_app.core.network.api.TicketsApi
import com.example.teatrope_kotlin_app.ticket.data.repository.TicketRepository
import com.example.teatrope_kotlin_app.ticket.data.repository.TicketRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TicketModule {
    @Binds
    @Singleton
    abstract fun bindTicketRepository(
        impl: TicketRepositoryImpl
    ): TicketRepository
}