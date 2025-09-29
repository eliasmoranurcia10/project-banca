package com.apibancario.controller;

import com.apibancario.exception.BadRequestException;
import com.apibancario.exception.ResourceNotFoundException;
import com.apibancario.model.dto.cliente.ClientRequestDto;
import com.apibancario.model.dto.cliente.ClientResponseDto;
import com.apibancario.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(ClienteController.class) // WebMvcTest: Carga solo el controlador
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // MockBean: Crea una versión simulada del servicio
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllClients() throws Exception {
        List<ClientResponseDto> clientesDto = List.of(
                new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com"),
                new ClientResponseDto(2,"José", "Damian","josedamian@gmail.com")
        );
        when(clienteService.listAll()).thenReturn(clientesDto);
        // MockMvc: Realiza peticiones simuladas al controlador
        mockMvc.perform(get("/clients").accept(MediaType.APPLICATION_JSON))
                // .andExpect(): Verifica código de estado y el contenido de respuesta.
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.size()").value(2) )
                .andExpect( jsonPath("$[0].clientId").value(1) )
                .andExpect( jsonPath("$[0].name").value("Elias") )
                .andExpect( jsonPath("$[0].lastName").value("Moran") )
                .andExpect( jsonPath("$[0].email").value("elias@gmail.com") )
                .andExpect( jsonPath("$[1].clientId").value(2) )
                .andExpect( jsonPath("$[1].name").value("José") )
                .andExpect( jsonPath("$[1].lastName").value("Damian") )
                .andExpect( jsonPath("$[1].email").value("josedamian@gmail.com") );
    }

    @Test
    void testGetClientById200() throws Exception {
        Integer id = 1;
        ClientResponseDto clientResponseDto =  new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        when(clienteService.findById(id)).thenReturn(clientResponseDto);
        mockMvc.perform(get("/clients/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.clientId").value(1) )
                .andExpect( jsonPath("$.name").value("Elias") )
                .andExpect( jsonPath("$.lastName").value("Moran") )
                .andExpect( jsonPath("$.email").value("elias@gmail.com") );
    }

    @Test
    void testGetClientById400() throws Exception {
        Integer id = 0;
        when(clienteService.findById(id)).thenThrow(new BadRequestException("El id es incorrecto"));
        mockMvc.perform(get("/clients/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("bad-request"))
                .andExpect(jsonPath("$.message").value("El id es incorrecto"));
    }

    @Test
    void testGetClientById404() throws Exception {
        Integer id = 99;
        when(clienteService.findById(id)).thenThrow(new ResourceNotFoundException("No se encontró al cliente con id: "+id));
        mockMvc.perform(get("/clients/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("not-found"))
                .andExpect(jsonPath("$.message").value("No se encontró al cliente con id: "+id));
    }

    @Test
    void testSaveClient200() throws Exception {
        ClientRequestDto clientRequestDto = new ClientRequestDto("Elias", "Moran", "75484848", "elias@gmail.com");
        ClientResponseDto clientResponseDto =  new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        when(clienteService.save(clientRequestDto)).thenReturn(clientResponseDto);
        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientRequestDto)))
                .andDo( print() )
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.clientId").value(1) )
                .andExpect( jsonPath("$.name").value("Elias") )
                .andExpect( jsonPath("$.lastName").value("Moran") )
                .andExpect( jsonPath("$.email").value("elias@gmail.com") );
    }

    @Test
    void testUpdateClient200() throws Exception {
        Integer id = 1;
        ClientRequestDto clientRequestDto = new ClientRequestDto("Elias", "Moran", "75484848", "eliasmoran@gmail.com");
        ClientResponseDto clientResponseDto =  new ClientResponseDto(1,"Elias", "Moran","eliasmoran@gmail.com");
        when(clienteService.update(id, clientRequestDto)).thenReturn(clientResponseDto);
        mockMvc.perform(put("/clients/"+id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientRequestDto)))
                .andDo( print() )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value(1))
                .andExpect(jsonPath("$.name").value("Elias"))
                .andExpect(jsonPath("$.lastName").value("Moran"))
                .andExpect(jsonPath("$.email").value("eliasmoran@gmail.com"));
    }

    @Test
    void testDeleteClient204() throws Exception {
        Integer id = 1;
        doNothing().when(clienteService).delete(id);
        mockMvc.perform(delete("/clients/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void testSaveClient400() throws Exception{
        ClientRequestDto clientRequestDtoBad = new ClientRequestDto("", "", "75484848", "elias@gmail.com");
        when(clienteService.save(clientRequestDtoBad)).thenThrow(new BadRequestException("Error de validacion"));
        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(clientRequestDtoBad)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
