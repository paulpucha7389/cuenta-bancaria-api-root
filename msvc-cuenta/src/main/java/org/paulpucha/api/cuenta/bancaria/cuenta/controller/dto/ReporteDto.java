package org.paulpucha.api.cuenta.bancaria.cuenta.controller.dto;

import java.math.BigDecimal;
import java.util.Date;

public interface ReporteDto {

        public Date getFecha();

        public String getCliente();

        public int getNumeroCuenta();

        public String getTipo();

        public BigDecimal getSaldoInicial();

        public String getEstado();

        public BigDecimal getMovimiento();

        public BigDecimal getSaldoDisponible();

        public String getTipoMovimiento();

    }
