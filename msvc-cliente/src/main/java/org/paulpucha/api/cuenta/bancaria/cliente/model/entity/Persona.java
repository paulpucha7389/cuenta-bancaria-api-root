package org.paulpucha.api.cuenta.bancaria.cliente.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
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
 * <b> Clase persona que sera heradad por cliente. </b>
 * 
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *          <p>
 *          [$Author: Paul Pucha $, $Date: 09 jul. 2024 $]
 *          </p>
 */
@MappedSuperclass
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	private String direccion;

	@Column(length = 3)
	private int edad;

	@Column(length = 20)
	private String genero;

	@Column(unique = true, length = 10)
	private String identificacion;

	@Column(length = 60)
	private String nombre;

	@Column(length = 10)
	private String telefono;

}