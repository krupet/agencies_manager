package com.krupet.agenciesmanagerserver.controllers

import com.krupet.agenciesmanagerserver.exceptions.EntityNotFoundException
import com.krupet.agenciesmanagerserver.model.ErrorMessage
import javax.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AgencyControllerAdvice {
    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handelMessageNotReadable(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorMessage> {
        return ResponseEntity(
            ErrorMessage(
                timestamp = System.currentTimeMillis(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.name,
                path = request.servletPath,
                message = ex.missingPropertyMessage
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handelMessageNotValid(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorMessage> {
        return ResponseEntity(
            ErrorMessage(
                timestamp = System.currentTimeMillis(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.name,
                path = request.servletPath,
                message = ex.violations.joinToString(separator = ";")
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun handelEntityNotFound(
        ex: EntityNotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorMessage> {
        return ResponseEntity(
            ErrorMessage(
                timestamp = System.currentTimeMillis(),
                status = HttpStatus.BAD_REQUEST.value(),
                error = HttpStatus.BAD_REQUEST.name,
                path = request.servletPath,
                message = ex.message ?: ""
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}

private val missingPropertyRegex = "(JSON property [a-zA-Z]+ due to missing)".toRegex()

val HttpMessageNotReadableException.missingPropertyMessage: String
    get() {
        val find = missingPropertyRegex.find(this.message.toString())
        return find?.value?.replace("due to ", "") ?: ""
    }

val MethodArgumentNotValidException.violations: Collection<String>
    get() = this.bindingResult.allErrors.map { it.defaultMessage ?: "" }
