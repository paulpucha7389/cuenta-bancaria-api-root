package org.paulpucha.api.cuenta.bancaria.cuenta;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.paulpucha.api.cuenta.bancaria.cuenta.model.entity.Cuenta;
import org.paulpucha.api.cuenta.bancaria.cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CuentaTestIntegration {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService service;

    @Test
    public void testObtenerCuentasPorCliente_ValidIdentificacion_ReturnsOk() throws Exception {
        //Datos de prueba
        String identificacion = "1735453654";
        List<Cuenta> cuentas = new ArrayList<>();

        when(service.obtenerCuentasPorCliente(identificacion)).thenReturn(cuentas);

        // Realiza la petición y verifica la respuesta
        mockMvc.perform(get("/api/cuentas/{identificacion}", identificacion))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(cuentas.size()));
    }
}
