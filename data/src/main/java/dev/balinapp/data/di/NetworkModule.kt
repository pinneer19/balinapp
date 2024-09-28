package dev.balinapp.data.di

import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dev.balinapp.data.BuildConfig
import dev.balinapp.data.api.AuthService
import dev.balinapp.data.api.CommentService
import dev.balinapp.data.api.ImageService
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module(includes = [NetworkBindModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(client: OkHttpClient): Retrofit {
        val jsonConverterFactory = Json.asConverterFactory("application/json".toMediaType())

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(jsonConverterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageService(retrofit: Retrofit): ImageService {
        return retrofit.create(ImageService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentService(retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }
}