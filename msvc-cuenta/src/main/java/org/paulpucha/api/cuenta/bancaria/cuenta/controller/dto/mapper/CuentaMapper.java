package org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto.salida.CuentaSalidaDto;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Cuenta;

@Mapper(componentModel = "spring")
public interface CuentaMapper {
    CuentaSalidaDto toCuentaResponseDto(Cuenta cuenta);

}
