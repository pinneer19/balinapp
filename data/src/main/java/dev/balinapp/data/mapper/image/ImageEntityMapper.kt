package dev.balinapp.data.mapper.image

import dev.balinapp.data.db.entity.ImageEntity
import dev.balinapp.domain.model.image.ImageOutput

fun ImageEntity.toImageOutput(): ImageOutput = with(this) {
    ImageOutput(
        id = id,
        url = url,
        date = date,
        lat = lat,
        lng = lng
    )
}