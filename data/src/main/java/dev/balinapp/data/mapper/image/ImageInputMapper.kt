package dev.balinapp.data.mapper.image

import dev.balinapp.data.model.image.ImageDtoIn
import dev.balinapp.domain.model.image.ImageInput

fun ImageInput.toImageDtoIn(): ImageDtoIn = with(this) {
    ImageDtoIn(
        base64Image = base64Image,
        date = date,
        lat = lat,
        lng = lng
    )
}