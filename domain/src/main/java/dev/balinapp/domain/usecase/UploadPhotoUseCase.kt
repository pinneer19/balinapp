package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageInput
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.repository.ImageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageInput: ImageInput): RequestResult<ImageOutput> {
        return imageRepository.uploadImage(imageInput)
    }
}