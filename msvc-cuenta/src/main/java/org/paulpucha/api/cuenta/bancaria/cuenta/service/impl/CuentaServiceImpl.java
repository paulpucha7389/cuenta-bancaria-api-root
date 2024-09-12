/**
 *
 */
package org.paulpucha.api.cuenta.bancaria.cuenta.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.paulpucha.api.cuenta.bancaria.cuenta.clients.ClienteClientRest;
import org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.entrada.CuentaEntradaDto;
import org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.salida.BaseResponseDto;
import org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.EstadoEmun;
import org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.paulpucha.api.cuenta.bancaria.cuenta.repository.CuentaRepository;
import org.paulpucha.api.cuenta.bancaria.cuenta.exception.CuentaException;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.ClienteModel;
import org.paulpucha.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * <b> Servicio para la Cuenta. </b>
 *
 * @author Paul Pucha
 * @version $Revision: 1.0 $
 *     <p>
 *     [$Author: Paul Pucha $, $Date: 08 jul. 2024 $]
 *     </p>
 */
@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteClientRest clienteRest;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = CuentaException.class)
    public Cuenta create(CuentaEntradaDto cuentaEntradaDto) throws CuentaException {
        String identificacion = cuentaEntradaDto.getIdentificacion();
        if (identificacion == null || identificacion.isBlank()) {
            throw new CuentaException("El campo identificación es requerido");
        }

        TipoCuentaEnum tipoCuenta;
        try {
            tipoCuenta = TipoCuentaEnum.fromString(String.valueOf(cuentaEntradaDto.getTipoCuenta()));
        } catch (IllegalArgumentException e) {
            throw new CuentaException("Tipo de cuenta inválido: debe ser AHORROS o CORRIENTE.");
        }

        try {
            ResponseEntity<BaseResponseDto> response = clienteRest.obtenerClientePorIdentificacion(cuentaEntradaDto.getIdentificacion());

            if (response.getStatusCode() == HttpStatus.OK) {
                if (!ObjectUtils.isEmpty(response.getBody())) {
                    BaseResponseDto baseResponseDto = response.getBody();
                    String jsonString = objectMapper.writeValueAsString(baseResponseDto.getData());
                    ClienteModel clienteEncontrado = objectMapper.readValue(jsonString, ClienteModel.class);

                    // Valida si el cliente está activo
                    if (!clienteEncontrado.isEstado()) {
                        throw new CuentaException("Cliente con estado inactivo.");
                    }

                    // Valida si el cliente ya tiene una cuenta
                    Optional<Cuenta> cuentaExistente = cuentaRepository.findByNumeroCuenta(cuentaEntradaDto.getNumeroCuenta());
                    if (cuentaExistente.isPresent()) {
                        throw new CuentaException("La cuenta ya existe.");
                    }

                    // Crea la cuenta si el cliente está activo y no tiene una cuenta existente
                    Cuenta cuenta = Cuenta.builder()
                            .estado(EstadoEmun.ACTIVO.getDescripcion())
                            .numeroCuenta(cuentaEntradaDto.getNumeroCuenta())
                            .saldoInicial(BigDecimal.valueOf(cuentaEntradaDto.getSaldoInicial()))
                            .tipoCuenta(tipoCuenta)
                            .idCliente(clienteEncontrado.getClienteId()).build();
                    return cuentaRepository.save(cuenta);
                }
            } else {
                // Manejar otros códigos de estado HTTP
                throw new CuentaException("Error al obtener cliente: " + response.getStatusCode());
            }
        }catch (JsonProcessingException e) {
            throw new CuentaException("Error al procesar JSON", e);
        } catch (Exception e) {
            throw new CuentaException("Cliente no encontrado, primero deber ser creado", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = CuentaException.class)
    @Override
    public Cuenta update(CuentaEntradaDto cuentaEntradaDto) throws CuentaException {

        // Buscar la cuenta existente
        Optional<Cuenta> cuenta = cuentaRepository.findByNumeroCuenta(cuentaEntradaDto.getNumeroCuenta());
        if (!cuenta.isPresent()) {
            throw new CuentaException("No existe la cuenta ingresada.");
        }

        Cuenta cuentaActualizar = cuenta.get();
        cuentaActualizar.setNumeroCuenta(cuentaEntradaDto.getNumeroCuenta());
        cuentaActualizar.setSaldoInicial(BigDecimal.valueOf(cuentaEntradaDto.getSaldoInicial()));
        TipoCuentaEnum tipoCuenta = TipoCuentaEnum.valueOf(String.valueOf(cuentaEntradaDto.getTipoCuenta()).toUpperCase());
        cuentaActualizar.setTipoCuenta(tipoCuenta);
        try {
            EstadoEmun estado = EstadoEmun.valueOf(cuentaEntradaDto.getEstado().toUpperCase());
            cuentaActualizar.setEstado(estado.name());
        } catch (IllegalArgumentException ex) {
            throw new CuentaException("Estado inválido. Solo se permiten los valores ACTIVO o INACTIVO.", ex);
        }
        return cuentaRepository.save(cuentaActualizar);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = CuentaException.class)
    @Override
    public void delete(Long id) throws CuentaException {
        try {
            Optional<Cuenta> cuenta = cuentaRepository.findById(id);
            if (!cuenta.isPresent()) {
                throw new CuentaException("No se puede eliminar la cuenta con id: " + id + " no existe.");
            }
            cuentaRepository.deleteById(cuenta.get().getIdCuenta());
        } catch (CuentaException e) {
            throw new CuentaException(e);
        }
    }

    /**
     * {Llamado Open Feing}
     */
    @Override
    public List<Cuenta> obtenerCuentasPorCliente(String identificacion) throws CuentaException {
        try {
            ResponseEntity<BaseResponseDto> response = clienteRest.obtenerClientePorIdentificacion(
                    identificacion);

            if (response.getStatusCode() == HttpStatus.OK) {
                if (!ObjectUtils.isEmpty(response.getBody())) {
                    BaseResponseDto baseResponseDto = response.getBody();
                    String jsonString = objectMapper.writeValueAsString(baseResponseDto.getData());
                    ClienteModel clienteEncontrado = objectMapper.readValue(jsonString,
                            ClienteModel.class);

                    if (ObjectUtils.isEmpty(clienteEncontrado)) {
                        throw new CuentaException(
                                "No existe el cliente, favor registrarlo primero y luego crear la cuenta para obtener información.");
                    }

                    List<Cuenta> listaCuenta = cuentaRepository.findByIdCliente(
                            clienteEncontrado.getClienteId());
                    if (ObjectUtils.isEmpty(listaCuenta)) {
                        throw new CuentaException(
                                "No existe cuenta(s) para el número de identificación ingresado."
                                        + clienteEncontrado.getIdentificacion());
                    }
                    return listaCuenta;
                }
            }
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ArrayList<>();
    }

        /**
         * {@inheritDoc}
         */
        @Override
        public Optional<Cuenta> obtenerPorNumeroCuenta ( int numeroCuenta){
            return cuentaRepository.findByNumeroCuenta(numeroCuenta);
        }
    }
