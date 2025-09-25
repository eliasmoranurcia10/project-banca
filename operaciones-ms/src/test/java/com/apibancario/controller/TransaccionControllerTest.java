package com.apibancario.controller;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.tarjeta.CardResponseDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionRequestDto;
import com.apibancario.project_banca.model.dto.transaccion.TransactionResponseDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.model.enums.TipoTarjeta;
import com.apibancario.project_banca.model.enums.TipoTransaccion;
import com.apibancario.project_banca.service.TransaccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(TransaccionController.class)
public class TransaccionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransaccionService transaccionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionResponseDto transactionResponseDto;
    private TransactionRequestDto transactionRequestDto;

    @BeforeEach
    void sepUt() {
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        AccountResponseDto accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);
        CardResponseDto cardResponseDto = new CardResponseDto(1, "4552366895916954", TipoTarjeta.DEBITO, "09/30", accountResponseDto);
        transactionResponseDto = new TransactionResponseDto(
                1,
                TipoTransaccion.DEPOSITO,
                new BigDecimal("80.00"),
                "09/09/2025 05:16:42",
                cardResponseDto,
                null
        );
        transactionRequestDto = new TransactionRequestDto(
                TipoTransaccion.DEPOSITO,
                new BigDecimal("80.00"),
                "4552366895916954",
                "5058",
                null
        );
    }

    @Test
    void testGetAllTransactions200() throws Exception {
        when(transaccionService.listAll()).thenReturn(List.of(transactionResponseDto));
        mockMvc.perform(get("/transactions").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].transactionId").value(1))
                .andExpect(jsonPath("$[0].transactionType").value("DEPOSITO"))
                .andExpect(jsonPath("$[0].amount").value(80.00))
                .andExpect(jsonPath("$[0].date").value("09/09/2025 05:16:42"));
    }

    @Test
    void testGetTransactionById200() throws Exception{
        Integer id = 1;
        when(transaccionService.findById(id)).thenReturn(transactionResponseDto);
        mockMvc.perform(get("/transactions/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.transactionType").value("DEPOSITO"))
                .andExpect(jsonPath("$.amount").value(80.00))
                .andExpect(jsonPath("$.date").value("09/09/2025 05:16:42"));
    }

    @Test
    void testSaveTransaction201() throws Exception{
        when(transaccionService.save(transactionRequestDto)).thenReturn(transactionResponseDto);
        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(transactionRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.transactionType").value("DEPOSITO"))
                .andExpect(jsonPath("$.amount").value(80.00))
                .andExpect(jsonPath("$.date").value("09/09/2025 05:16:42"));
    }

}
