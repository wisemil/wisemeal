package wisemil.wisemeal.api.domain.advice

import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import wisemil.wisemeal.common.exception.base.WisemealBadRequestException
import wisemil.wisemeal.common.exception.base.WisemealSystemException
import wisemil.wisemeal.common.log.logger
import wisemil.wisemeal.common.response.ApiResponse
import wisemil.wisemeal.common.response.ApiResponseCode

@RestControllerAdvice
class WiseApiExceptionHandler {
    private val log = logger()

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<Nothing> {
        log.error("API Unknown Error: {}", e.toString(), e)
        return ApiResponse.fail()
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(WisemealSystemException::class)
    fun handleException(e: WisemealSystemException): ApiResponse<Nothing> {
        log.error("API System Error: {}", e.logMessage, e)
        return ApiResponse.fail(e.apiResponseCode, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WisemealBadRequestException::class)
    fun handleException(e: WisemealBadRequestException): ApiResponse<Nothing> {
        log.error("API Failed: {}", e.logMessage, e)
        return ApiResponse.fail(e.apiResponseCode, e.message)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MissingRequestHeaderException::class,
        MethodArgumentTypeMismatchException::class,
        MethodArgumentNotValidException::class,
        HttpMessageNotReadableException::class,
        BindException::class,
    )
    fun handleBardRequestException(e: Exception): ApiResponse<Nothing> {
        log.error("API Bad Request: {}", e.toString())
        return ApiResponse.fail(ApiResponseCode.BAD_REQUEST)
    }
}
