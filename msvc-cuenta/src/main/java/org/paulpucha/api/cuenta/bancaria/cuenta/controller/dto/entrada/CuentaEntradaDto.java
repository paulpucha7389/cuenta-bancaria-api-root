/**
 *
 */
package org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.entrada;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum;

/**
 *
 * <b> Clase Dto para la entrada de la cuenta. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *          </p>
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaEntradaDto {

    @Pattern(regexp = "^\\d{1,10}$", message = "permite solo dígitos del 0 al 9 y un máximo de 10 dígitos")
    private String identificacion;

    @NotNull(message = "es requerido")
    @Enumerated(EnumType.STRING)
    private TipoCuentaEnum tipoCuenta;

    @NotNull(message = "es requerido")
    @Positive(message = "permite solo valores positivos")
    private Double saldoInicial;

    String estado;

    @NotNull(message = "es requerido")
    @Positive(message = "permite solo valores positivos")
    private Integer numeroCuenta;
}
