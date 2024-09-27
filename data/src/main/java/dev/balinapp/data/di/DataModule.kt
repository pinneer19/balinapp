package dev.balinapp.data.di

import dagger.Binds
import dagger.Module
import dev.balinapp.data.repository.AuthRepositoryImpl
import dev.balinapp.data.repository.ImageRepositoryImpl
import dev.balinapp.domain.repository.AuthRepository
import dev.balinapp.domain.repository.ImageRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DataSourceModule::class, DatabaseModule::class])
interface DataModule {

    @Binds
    @Singleton
    fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindImageRepository(repository: ImageRepositoryImpl): ImageRepository
}