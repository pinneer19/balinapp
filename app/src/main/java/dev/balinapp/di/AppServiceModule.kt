package dev.balinapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.balinapp.util.LocationClient
import dev.balinapp.util.LocationClientImpl
import javax.inject.Singleton

@Module
class AppServiceModule {

    @Provides
    @Singleton
    fun provideLocationClient(context: Context): LocationClient {
        return LocationClientImpl.newInstance(context)
    }
}