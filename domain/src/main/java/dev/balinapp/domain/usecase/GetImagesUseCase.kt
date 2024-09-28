package dev.balinapp.domain.usecase

import dev.balinapp.domain.model.image.ImageOutput
import dev.balinapp.domain.repository.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(): List<ImageOutput> {
        return imageRepository.getImages()
    }
}