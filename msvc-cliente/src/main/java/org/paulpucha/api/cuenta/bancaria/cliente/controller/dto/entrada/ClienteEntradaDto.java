/**
 * 
 */
package org.paulpucha.api.cuenta.bancaria.cliente.controller.dto.entrada;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * <b> Clase DTO para la entrada de los datos del cliente. </b>
 * 
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Paul Pucha $, $Date: 09 jul. 2024 $]
 *          </p>
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class ClienteEntradaDto {

	@NotNull(message = "es requerido ")
	@NotBlank(message = "requiere un número de identificación válido")
	@Pattern(regexp = "^\\d{1,10}$", message = "permite solo dígitos del 0 al 9 y un máximo de 10 dígitos")
	@Size(min = 1, max = 10, message = "permite solo un máximo de 10 dígitos")
	private String identificacion;

	@NotNull(message = "es requerdio")
	@NotBlank(message = "requiere una contraseña")
	private String contrasena;

	@NotNull(message = "es requerido")
	private Boolean estado;

	@NotNull(message = "es requerido")
	@NotBlank(message = "es requerido")
	private String direccion;

	@NotNull(message = "es requerido")
	@Max(value=105, message = "permite un valor hasta 105")
	@Min(value=18, message = "permite un valor hasta 18")
	@Positive(message = "permite solo valores positivos")
	private int edad;

	@NotNull(message = "es requerido")
	@NotBlank(message = "requiere genero Masculino o Femwnino")
	private String genero;

	@NotNull(message = "es requerido")
	@NotBlank(message = "debe ser llenado")
	private String nombre;

	@NotNull(message = "es requerido")
	@NotBlank(message = "requiere un teléfono válido")
	@Pattern(regexp = "^\\d{1,10}$", message = "permite solo dígitos del 0 al 9 y un máximo de 10 dígitos")
	@Size(min = 1, max = 10, message = "permite solo un máximo de 10 dígitos")
	private String telefono;
}
