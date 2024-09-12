package org.paulpucha.api.cuenta.bancaria.cliente.exception;

import org.paulpucha.api.cuenta.bancaria.cliente.controller.dto.salida.BaseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerExcepcion {

    @ExceptionHandler(ClienteException.class)
    public ResponseEntity<?> handleCuentaException(ClienteException ex) {
        int statusCode = ex.getErrorCode() != null ? ex.getErrorCode() : HttpStatus.BAD_REQUEST.value();

        BaseResponseDto<Object> response = BaseResponseDto.builder()
                .code(statusCode)
                .message(ex.getMessage())
                .errors(null)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(ClienteException ex) {
        int statusCode = ex.getErrorCode() != null ? ex.getErrorCode() : HttpStatus.BAD_REQUEST.value();

        BaseResponseDto<Object> response = BaseResponseDto.builder()
                .code(statusCode)
                .message(ex.getMessage())
                .errors(null)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String mensaje = "Error al procesar la solicitud";
        BaseResponseDto<Object> response = BaseResponseDto.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(mensaje)
                .errors(obtenerMensajeError(ex))
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String obtenerMensajeError(HttpMessageNotReadableException ex) {
        // Extraer el mensaje específico de la excepción o devolver un mensaje general
        return "Estado inválido: debe ser 'true' o 'false'.";
    }

}
