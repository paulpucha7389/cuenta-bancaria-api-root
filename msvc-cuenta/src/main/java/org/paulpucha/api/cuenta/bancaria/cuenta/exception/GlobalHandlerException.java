package org.paulpucha.api.cuenta.bancaria.cuenta.exception;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.salida.BaseResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.Serializable;
@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(CuentaException.class)
    public ResponseEntity<BaseResponseDto> handleCuentaException(CuentaException ex){
        int statusCode = ex.getErrorCode() != null ? ex.getErrorCode() : HttpStatus.BAD_REQUEST.value();

        BaseResponseDto response = BaseResponseDto.builder()
                .code(statusCode)
                .message(ex.getMessage())
                .errors(null)
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Serializable statusCode = ex.getRootCause() !=null ? ex.getRootCause() : HttpStatus.CONFLICT.value();

        BaseResponseDto response = new BaseResponseDto().builder()
                .code((HttpStatus.CONFLICT.value()))
                .message(ex.getMessage())
                .errors("Error de integridad de informacion")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponseDto> handleIllgalArgumentException(IllegalArgumentException ex){

        BaseResponseDto response = new BaseResponseDto().builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .errors("Error en el ingreso de datos")
                .data(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<BaseResponseDto> handleFeignException(FeignException ex) {
        // Extrae el cuerpo de la respuesta de error
        String message = extractErrorMessageFromFeignException(ex);
        HttpStatus status = HttpStatus.valueOf(ex.status());

        BaseResponseDto response = BaseResponseDto.builder()
                .code(status.value())
                .message(message)
                .errors("Error enviado desde el microservicio de cliente")
                .data(null)
                .build();

        return new ResponseEntity<>(response, status);
    }

    private String extractErrorMessageFromFeignException(FeignException feignException) {
        try {
            // Extrae el cuerpo de la respuesta de error
            String responseBody = feignException.contentUTF8();

            // Convertir el cuerpo de respuesta JSON en un objeto JsonNode para obtener solo el mensaje
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            // Retornar solo el valor del campo "message"
            return root.path("message").asText();
        } catch (Exception e) {
            // Manejar excepción durante el procesamiento del mensaje de Feign
            return "Error desconocido al obtener información del cliente.";
        }
    }
}
