package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.RequestResult
import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(imageId: Int): RequestResult<ImageOutput> {
        return imageRepository.deleteImage(imageId = imageId)
    }
}