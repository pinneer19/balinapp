package dev.balinapp.data.di

import dagger.Binds
import dagger.Module
import dev.balinapp.data.repository.AuthRepositoryImpl
import dev.balinapp.domain.repository.AuthRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DataSourceModule::class])
interface DataModule {

    @Binds
    @Singleton
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository
}