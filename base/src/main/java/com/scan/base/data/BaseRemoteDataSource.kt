package android.ptc.com.ptcflixing.base.data

import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

open class BaseRemoteDataSource {

    @Throws(
        ApiException::class,
        DataNotFoundException::class,
        NotFoundException::class
    )
    protected suspend fun <T : Any> makeRequest(call: suspend () -> Response<T>): T {
        return safeApiResult(call)
    }

    @Throws(
        ApiException::class,
        DataNotFoundException::class,
        NotFoundException::class
    )
    private suspend fun <T : Any> safeApiResult(call: suspend () -> Response<T>): T {
        val result = call()
        return when (result.isSuccessful) {
            false -> {
                val responseCode = result.code()
                val errorResponse = getErrorResponse(result.errorBody())
                when {
                    responseCode == 404 -> throw NotFoundException()
                    errorResponse?.message != null -> throw ServerMessageApiException(
                        errorResponse.message,
                        responseCode
                    )
                    else -> throw ApiException(
                        responseCode,
                        "API response is not successful (code: $responseCode)"
                    )
                }
            }
            true -> {
                when (result.code()) {
                    200 -> result.body()!!
                    else -> result.body()!!
                }
            }
        }
    }

    private fun getErrorResponse(errorBody: ResponseBody?): BaseResponse? {
        return try {
            Gson().fromJson(
                convertResponseBodyToString(errorBody),
                BaseResponse::class.java
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun convertResponseBodyToString(responseBody: ResponseBody?): String {
        try {
            return responseBody!!.string()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}