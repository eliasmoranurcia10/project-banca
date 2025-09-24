package com.apibancario.project_banca.controller;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayRequestDto;
import com.apibancario.project_banca.model.dto.pagoprestamo.LoanPayResponseDto;
import com.apibancario.project_banca.model.dto.prestamo.LoanResponseDto;
import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import com.apibancario.project_banca.service.PagoPrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(PagoPrestamoController.class)
public class PagoPrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoPrestamoService pagoPrestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoanPayResponseDto loanPayResponseDto;
    private LoanPayRequestDto loanPayRequestDto;

    @BeforeEach
    void setUp() {
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        LoanResponseDto loanResponseDto = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.APROBADO, clientResponseDto );
        loanPayResponseDto = new LoanPayResponseDto(1, new BigDecimal("400"), "19/09/2025 11:42:05", loanResponseDto);
        loanPayRequestDto = new LoanPayRequestDto(new BigDecimal("400"), 1);
    }

    @Test
    void testGetAllLoanPayments200() throws Exception {
        when(pagoPrestamoService.listAll()).thenReturn(List.of(loanPayResponseDto));
        mockMvc.perform(get("/loan-payments").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].payId").value(1))
                .andExpect(jsonPath("$[0].paymentAmount").value(400))
                .andExpect(jsonPath("$[0].paymentDate").value("19/09/2025 11:42:05"));
    }

    @Test
    void testGetLoanPayById200() throws Exception{
        Integer id = 1;
        when(pagoPrestamoService.findById(id)).thenReturn(loanPayResponseDto);
        mockMvc.perform(get("/loan-payments/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payId").value(1))
                .andExpect(jsonPath("$.paymentAmount").value(400))
                .andExpect(jsonPath("$.paymentDate").value("19/09/2025 11:42:05"));
    }

    @Test
    void testSaveLoanPay201() throws Exception{
        when(pagoPrestamoService.save(loanPayRequestDto)).thenReturn(loanPayResponseDto);
        mockMvc.perform(post("/loan-payments").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loanPayRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.payId").value(1))
                .andExpect(jsonPath("$.paymentAmount").value(400))
                .andExpect(jsonPath("$.paymentDate").value("19/09/2025 11:42:05"));
    }
}
