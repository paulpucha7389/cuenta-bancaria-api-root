package org.paulpucha.api.cuenta.bancaria.cliente.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import org.paulpucha.api.cuenta.bancaria.cliente.exception.ClienteException;
import org.paulpucha.api.cuenta.bancaria.cliente.service.ClienteService;
import org.paulpucha.api.cuenta.bancaria.cliente.controller.dto.entrada.ClienteEntradaDto;
import org.paulpucha.api.cuenta.bancaria.cliente.controller.dto.salida.BaseResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b> Clase controlador de los clientes. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *     </p>
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private static final Logger log = LoggerFactory.getLogger(ClienteController.class);
    private static final String ERROR_MN = "Ha ocurrido un error {}";

    @Autowired
    private ClienteService service;

    /**
     * <b> Metodo que crea un cliente. </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @param clienteEntradaDto parametro de entrada
     * @return ResponseEntity<BaseResponseDto> objeto o mensaje de error
     */
    @PostMapping
    public ResponseEntity<BaseResponseDto> guardar(
            @Valid @RequestBody ClienteEntradaDto clienteEntradaDto,
            BindingResult resultado) {
        try {
            if (resultado.hasErrors()) {
                return validar(resultado);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(BaseResponseDto.builder().code(201).message("Cliente creado correctamente").data(service.create(clienteEntradaDto)).build());
        } catch (ClienteException e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            Integer errorCode = e.getErrorCode() != null ? e.getErrorCode() : 400;
            log.error(ERROR_MN, errorMessage);
            return ResponseEntity.badRequest()
                    .body(BaseResponseDto.builder().code(errorCode).message(errorMessage).build());
        }
    }

    /**
     * <b> Metodo para tratar los errores de validaciones de los datos de entrada. </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @param resultado parametro de entrada
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     */
    private static ResponseEntity<BaseResponseDto> validar(
            BindingResult resultado) {
        Map<String, String> errores = new HashMap<>();
        resultado.getFieldErrors().forEach(error -> {
            errores.put(error.getField(),
                    "El campo " + error.getField() + " " + error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(BaseResponseDto.builder().code(400).message("Error en la entrada de datos").errors(errores).build());
    }

    /**
     * <b> Metodo para obtiene un cliente por su identificacion. </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @param identificacion parametro de entrada
     * @return ResponseEntity<BaseResponseDto> objeto o mensaje de error
     */
    @GetMapping(path = "/{identificacion}")
    public ResponseEntity<BaseResponseDto> obtenerClientePorIdentificacion(@PathVariable String identificacion) {
        try {
            return ResponseEntity.ok().body(
                    BaseResponseDto.builder().code(HttpStatus.OK.value()).data(service.obtenerPorIdentificacion(identificacion))
                            .build());
        } catch (ClienteException e) {
            String errorMessage = (e.getCause() != null) ? e.getCause().getMessage() : e.getMessage();
            log.error(ERROR_MN, errorMessage);
            return ResponseEntity.badRequest()
                    .body(BaseResponseDto.builder().code(HttpStatus.BAD_REQUEST.value()).message(errorMessage).build());
        }
    }

    /**
     * <b> Metodo para obtiene todos los clientes </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @return ResponseEntity<BaseResponseDto> lista o mensaje de error
     */
    @GetMapping()
    public ResponseEntity<BaseResponseDto> obtenerTodosClientes() {
        try {
            return ResponseEntity.ok().body(BaseResponseDto.builder().code(HttpStatus.OK.value()).data(service.read()).build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.badRequest()
                    .body(BaseResponseDto.builder().code(HttpStatus.BAD_REQUEST.value()).message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que actualiza un cliente. </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @param clienteEntradaDto parametro de entrada
     * @return ResponseEntity<BaseResponseDto> objeto o mensaje de error
     */
    @PutMapping
    public ResponseEntity<BaseResponseDto> actualizar(
            @Valid @RequestBody ClienteEntradaDto clienteEntradaDto,
            BindingResult resultado) {
        try {
            if (resultado.hasErrors()) {
                return validar(resultado);
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(BaseResponseDto.builder().code(HttpStatus.CREATED.value()).message("Cliente actualizado correctamente").data(service.update(clienteEntradaDto)).build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.badRequest()
                    .body(BaseResponseDto.builder().code(HttpStatus.BAD_REQUEST.value()).message(e.getCause().getMessage()).build());
        }
    }

    /**
     * <b> Metodo que elimina un registro por su id. </b>
     * <p>
     * [Author: Paul Pucha, Date: 09 jul. 2024]
     * </p>
     *
     * @param identificacion parametro de entrada
     * @return ResponseEntity<BaseResponseDto> mensaje de error
     */
    @DeleteMapping("/{identificacion}")
    public ResponseEntity<BaseResponseDto> eliminar(
            @PathVariable("identificacion") String identificacion) {
        try {
            service.delete(identificacion);
            return ResponseEntity.ok()
                    .body(BaseResponseDto.builder().code(HttpStatus.OK.value()).message("Registro Eliminado").build());
        } catch (Exception e) {
            log.error(ERROR_MN, e.getCause().getMessage());
            return ResponseEntity.badRequest()
                    .body(BaseResponseDto.builder().code(HttpStatus.BAD_REQUEST.value()).message(e.getCause().getMessage()).build());
        }
    }
}
