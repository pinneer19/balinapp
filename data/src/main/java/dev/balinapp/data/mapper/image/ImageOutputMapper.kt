package dev.balinapp.data.mapper.image

import dev.balinapp.data.db.entity.ImageEntity
import dev.balinapp.domain.model.image.ImageOutput

fun ImageOutput.toImageEntity(): ImageEntity = with(this) {
    ImageEntity(
        id = id,
        url = url,
        date = date,
        lat = lat,
        lng = lng
    )
}