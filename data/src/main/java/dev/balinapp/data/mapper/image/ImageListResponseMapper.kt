package dev.balinapp.data.mapper.image

import dev.balinapp.data.db.entity.ImageEntity
import dev.balinapp.data.model.image.ImageDtoOut


fun ImageDtoOut.toImageEntity(): ImageEntity = with(this) {
    return ImageEntity(
        id = id ?: -1,
        url = url.orEmpty(),
        date = date ?: -1,
        lat = lat ?: -1.0,
        lng = lng ?: -1.0,
    )
}