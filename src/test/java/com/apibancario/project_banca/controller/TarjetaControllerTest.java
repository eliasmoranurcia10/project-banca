package com.apibancario.project_banca.controller;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardPinRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardRequestDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import com.apibancario.project_banca.service.TarjetaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

@WebMvcTest(TarjetaController.class)
public class TarjetaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TarjetaService tarjetaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CardResponseDto cardResponseDto;
    private CardRequestDto cardRequestDto;

    @BeforeEach
    void setUp() {
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        AccountResponseDto accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);
        cardRequestDto = new CardRequestDto(TipoTarjeta.DEBITO,"5058", 1);
        cardResponseDto = new CardResponseDto(1, "4552366895916954", TipoTarjeta.DEBITO, "09/30", accountResponseDto);
    }

    @Test
    void testGetAllCards200() throws Exception{
        when(tarjetaService.listAll()).thenReturn(List.of(cardResponseDto));
        mockMvc.perform(get("/cards").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].cardId").value(1))
                .andExpect(jsonPath("$[0].cardNumber").value("4552366895916954"))
                .andExpect(jsonPath("$[0].cardType").value("DEBITO"))
                .andExpect(jsonPath("$[0].expirationDate").value("09/30"));
    }

    @Test
    void testGetCardById200() throws Exception{
        Integer id = 1;
        when(tarjetaService.findById(id)).thenReturn(cardResponseDto);
        mockMvc.perform(get("/cards/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value(1))
                .andExpect(jsonPath("$.cardNumber").value("4552366895916954"))
                .andExpect(jsonPath("$.cardType").value("DEBITO"))
                .andExpect(jsonPath("$.expirationDate").value("09/30"));
    }

    @Test
    void testSaveCard201() throws Exception{
        when(tarjetaService.save(cardRequestDto)).thenReturn(cardResponseDto);
        mockMvc.perform(post("/cards").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cardRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardId").value(1))
                .andExpect(jsonPath("$.cardNumber").value("4552366895916954"))
                .andExpect(jsonPath("$.cardType").value("DEBITO"))
                .andExpect(jsonPath("$.expirationDate").value("09/30"));
    }

    @Test
    void testUpdatePinCard() throws Exception{
        Integer id = 1;
        CardPinRequestDto cardPinRequestDto = new CardPinRequestDto("5058","0303");
        when(tarjetaService.updatePinTarjeta(id, cardPinRequestDto)).thenReturn(cardResponseDto);
        mockMvc.perform(patch("/cards/"+id+"/cambiar-pin").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(cardPinRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value(1))
                .andExpect(jsonPath("$.cardNumber").value("4552366895916954"))
                .andExpect(jsonPath("$.cardType").value("DEBITO"))
                .andExpect(jsonPath("$.expirationDate").value("09/30"));
    }

    @Test
    void testDeleteCard() throws Exception {
        Integer id = 1;
        doNothing().when(tarjetaService).delete(id);
        mockMvc.perform(delete("/cards/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
