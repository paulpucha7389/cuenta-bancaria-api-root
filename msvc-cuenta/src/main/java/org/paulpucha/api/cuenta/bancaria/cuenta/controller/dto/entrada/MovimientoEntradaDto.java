/**
 * 
 */
package org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.entrada;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * <b> Clase Dto para la entrada del movimiento. </b>
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
public class MovimientoEntradaDto {

	@NotNull(message = "es requerido")
	@NotEmpty(message = "requiere tipo movimiento 'debito o 'credito'")
	private String tipoMovimiento;

	@NotNull(message = "es requerido")
	@Positive(message = "solo permite valores positivos")
	private Double valor;

	@NotNull(message = "es requerido")
	@Positive(message = "solo permite valores positivos")
	private Integer numeroCuenta;

}
