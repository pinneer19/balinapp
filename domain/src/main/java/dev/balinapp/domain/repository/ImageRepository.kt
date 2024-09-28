package dev.balinapp.domain.repository

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageInput
import dev.balinapp.domain.model.image.ImageOutput

interface ImageRepository {

    suspend fun uploadImage(imageInput: ImageInput): RequestResult<ImageOutput>

    suspend fun deleteImage(imageId: Int): RequestResult<ImageOutput>

    suspend fun getImageById(id: Int): ImageOutput

    suspend fun getImages(): List<ImageOutput>
}