package dev.balinapp.data.mapper

import dev.balinapp.domain.model.RequestResult

internal fun <T : Any> Result<T>.toRequestResult(): RequestResult<T> {
    return when {
        isSuccess -> RequestResult.Success(data = getOrThrow())
        isFailure -> RequestResult.Error(error = exceptionOrNull())
        else -> error("Impossible branch")
    }
}