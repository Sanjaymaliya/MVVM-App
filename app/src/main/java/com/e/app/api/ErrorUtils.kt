package com.e.app.api

import retrofit2.Response
import java.io.IOException

object ErrorUtils {

    fun parseError(response: Response<*>): ApiError {
        val converter = ApiInitialize.initialize().responseBodyConverter<ApiError>(ApiError::class.java, arrayOfNulls<Annotation>(0))
        val error: ApiError
        try {
            error = converter.convert(response.errorBody())!!
        } catch (e: IOException) {
            return ApiError()
        }

        return error
    }
}
