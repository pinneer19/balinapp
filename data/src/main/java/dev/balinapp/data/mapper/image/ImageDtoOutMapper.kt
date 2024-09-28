package dev.balinapp.data.mapper.image

import dev.balinapp.data.model.image.ImageDtoOut
import dev.balinapp.domain.model.image.ImageOutput

fun ImageDtoOut?.toImageOutput(): ImageOutput {
    return this?.run {
        ImageOutput(
            id = id ?: -1,
            url = url.orEmpty(),
            date = date ?: -1,
            lat = lat ?: -1.0,
            lng = lng ?: -1.0,
        )
    } ?: ImageOutput(
        id = -1,
        url = "",
        date = -1,
        lat = -1.0,
        lng = -1.0,
    )
}