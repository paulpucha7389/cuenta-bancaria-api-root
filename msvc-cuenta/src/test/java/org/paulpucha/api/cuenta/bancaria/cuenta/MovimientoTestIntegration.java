package org.paulpucha.api.cuenta.bancaria.cuenta;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.paulpucha.api.cuenta.bancaria.cuenta.controller.MovimientoController;
import org.paulpucha.api.cuenta.bancaria.cuenta.enumeration.TipoCuentaEnum;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Movimiento;
import org.paulpucha.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.paulpucha.api.cuenta.bancaria.cuenta.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

@WebMvcTest(MovimientoController.class)
public class MovimientoTestIntegration {

    @MockBean
    private MovimientoService movimientoService;

    @MockBean
    private CuentaService cuentaService;

    @MockBean
    private BindingResult bindingResult;

    @Autowired
    private MockMvc mvc;

    @Test
    void whenObtenerPorNumeroCuentaThenNotNull() throws Exception {
        //Given
        Movimiento movimientoEsperado_uno = Movimiento.builder().idMovimiento(1L).idCuenta(1L)
                .tipoMovimiento("credito").valor(BigDecimal.valueOf(100L))
                .saldo(BigDecimal.valueOf(1100L)).saldoAnterior(BigDecimal.valueOf(1000L)).build();
        Movimiento movimientoEsperado_dos = Movimiento.builder().idMovimiento(1L).idCuenta(1L)
                .tipoMovimiento("debito").valor(BigDecimal.valueOf(100L))
                .saldo(BigDecimal.valueOf(1000L)).saldoAnterior(BigDecimal.valueOf(1100L)).build();
        List<Movimiento> listaMovimientoEsperado = new ArrayList<>();
        listaMovimientoEsperado.add(movimientoEsperado_uno);
        listaMovimientoEsperado.add(movimientoEsperado_dos);
        Cuenta cuentaEsperada = Cuenta.builder().numeroCuenta(456789).idCuenta(1L).idCliente(1L).
                tipoCuenta(TipoCuentaEnum.AHORROS).saldoInicial(BigDecimal.valueOf(1000L)).estado("True").build();
        //When
        when(bindingResult.hasErrors()).thenReturn(false);
        when(movimientoService.obtenerPorNumeroCuenta(456789)).thenReturn(listaMovimientoEsperado);

        mvc.perform(get("/api/movimientos/456789").contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk());

        verify(movimientoService).obtenerPorNumeroCuenta(456789);
    }


}
