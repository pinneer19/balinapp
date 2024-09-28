package dev.balinapp.data.api

import dev.balinapp.data.model.auth.AuthDataDto
import dev.balinapp.data.model.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("account/signup")
    suspend fun signUp(@Body userDto: AuthDataDto): Result<AuthResponse>

    @POST("account/signin")
    suspend fun signIn(@Body userDto: AuthDataDto): Result<AuthResponse>
}