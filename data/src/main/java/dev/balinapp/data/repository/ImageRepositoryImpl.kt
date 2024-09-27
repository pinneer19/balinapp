package dev.balinapp.data.repository

import dev.balinapp.data.api.ImageService
import dev.balinapp.data.db.dao.ImageDao
import dev.balinapp.data.mapper.image.toImageDtoIn
import dev.balinapp.data.mapper.image.toImageOutput
import dev.balinapp.data.mapper.toRequestResult
import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageInput
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.model.map
import dev.balinapp.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val imageService: ImageService,
    private val imageDao: ImageDao
) : ImageRepository {

    override suspend fun uploadImage(imageInput: ImageInput): RequestResult<ImageOutput> {
        return imageService
            .postImage(imageInput.toImageDtoIn())
            .toRequestResult()
            .map { it.data.toImageOutput() }
    }

    override suspend fun deleteImage(imageId: Int): RequestResult<ImageOutput> {
        return imageService
            .deleteImage(imageId)
            .toRequestResult()
            .map { it.data.toImageOutput() }
    }

    override suspend fun getImageById(id: Int): ImageOutput {
        return imageDao.getImageById(id).toImageOutput()
    }
}