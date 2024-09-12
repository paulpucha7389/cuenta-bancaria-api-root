package org.paulpucha.api.cuenta.bancaria.cliente.controller.dto.salida;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <b> Clase comun de respuesta para los ws. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *     </p>
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseDto<T> {
    //Patron Builder
    private Integer code;
    private String message;
    private Object errors;
    private T data;
}