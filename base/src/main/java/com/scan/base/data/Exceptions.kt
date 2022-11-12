package android.ptc.com.ptcflixing.base.data

class NoInternetException(message: String? = "Not connected to internet") :
    RuntimeException(message)

/**
 * General network API exception.
 */
open class ApiException(val code: Int, message: String? = "Code: $code") : RuntimeException(message)

/**
 * Network API exception thrown when response code is 401 (unauthorized).
 */
class SuccessFalseException(code: Int = 200, message: String? = "Product Not Found 200") :
    ApiException(code, message)

/**
 * Network API exception thrown when response is 500 (server error).
 */
class NotFoundException(
    code: Int = 404,
    message: String? = "Not Found $code"
) :
    ApiException(code, message)

class ServerMessageApiException(
    private val serverLocalizedMessage: String?,
    code: Int,
    exceptionMessage: String? = "(Code: $code) Localized backend error message: $serverLocalizedMessage"
) : ApiException(code, exceptionMessage)

