package dev.balinapp.data.di

import dagger.Binds
import dagger.Module
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
interface NetworkBindModule {

    @Binds
    @Singleton
    fun provideAuthInterceptor(authInterceptor: AuthInterceptor): Interceptor
}