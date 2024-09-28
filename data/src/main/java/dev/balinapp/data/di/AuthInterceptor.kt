package dev.balinapp.data.di

import dev.balinapp.data.datasource.token.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking { tokenDataStore.getToken() }
        var request = chain.request()

        if (token.isNotEmpty()) {
            request = request.newBuilder()
                .addHeader(ACCESS_TOKEN, token)
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val ACCESS_TOKEN = "Access-Token"
    }
}