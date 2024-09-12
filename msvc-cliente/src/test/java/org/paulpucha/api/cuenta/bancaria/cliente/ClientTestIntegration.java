package org.paulpucha.api.cuenta.bancaria.cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.paulpucha.api.cuenta.bancaria.cliente.model.entity.Cliente;
import org.paulpucha.api.cuenta.bancaria.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientTestIntegration {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @AfterEach
    public void limpiarBaseDatos() {
        clienteRepository.deleteAll();
    }

    @Test
    public void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNombre("Jose Lema");
        cliente.setGenero("Masculino");
        cliente.setEdad(26);
        cliente.setIdentificacion("1735453099");
        cliente.setDireccion("Otavalo sn y principal");
        cliente.setTelefono("098874587");
        cliente.setContrasena("1234");
        cliente.setEstado(true);

        String clienteJson = objectMapper.writeValueAsString(cliente);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clienteJson))

                .andExpect(status().isCreated());
    }
}