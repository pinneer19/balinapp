package dev.balinapp.data.api

import dev.balinapp.data.model.image.ImageActionResult
import dev.balinapp.data.model.image.ImageDtoIn
import dev.balinapp.data.model.image.ImageListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageService {

    @GET("image")
    suspend fun getImages(@Query("page") page: Int): Result<ImageListResponse>

    @POST("image")
    suspend fun postImage(@Body image: ImageDtoIn): Result<ImageActionResult>

    @DELETE("image/{id}")
    suspend fun deleteImage(@Path("id") id: Int): Result<ImageActionResult>
}