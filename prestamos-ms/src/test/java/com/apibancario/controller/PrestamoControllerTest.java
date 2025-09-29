package com.apibancario.controller;

import com.apibancario.model.dto.prestamo.LoanRequestDto;
import com.apibancario.model.dto.prestamo.LoanResponseDto;
import com.apibancario.model.dto.prestamo.StatusLoanRequestDto;
import com.apibancario.model.enums.EstadoPrestamo;
import com.apibancario.service.PrestamoService;
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

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PrestamoService prestamoService;

    @Autowired
    protected ObjectMapper objectMapper;

    private LoanResponseDto loanResponseDto;
    private LoanRequestDto loanRequestDto;

    @BeforeEach
    void setUp() {
        loanRequestDto = new LoanRequestDto(new BigDecimal("4000"), new BigDecimal("0.20"),14, EstadoPrestamo.APROBADO, 1);
        loanResponseDto = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.APROBADO, 1 );
    }

    @Test
    void testGetAllLoans200() throws Exception {
        when(prestamoService.listAll()).thenReturn(List.of(loanResponseDto));
        mockMvc.perform(get("/loans").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].loanId").value(1))
                .andExpect(jsonPath("$[0].totalAmount").value(4000))
                .andExpect(jsonPath("$[0].interestRate").value(0.20))
                .andExpect(jsonPath("$[0].monthsOfDeadline").value(14));
    }

    @Test
    void testGetLoanById200() throws Exception{
        Integer id = 1;
        when(prestamoService.findById(id)).thenReturn(loanResponseDto);
        mockMvc.perform(get("/loans/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(4000))
                .andExpect(jsonPath("$.interestRate").value(0.20))
                .andExpect(jsonPath("$.monthsOfDeadline").value(14));
    }

    @Test
    void testSaveLoan201() throws Exception {
        when(prestamoService.save(loanRequestDto)).thenReturn(loanResponseDto);
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loanRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(4000))
                .andExpect(jsonPath("$.interestRate").value(0.20))
                .andExpect(jsonPath("$.monthsOfDeadline").value(14));
    }

    @Test
    void testUpdateStatusLoan200() throws Exception {
        Integer id = 1;
        StatusLoanRequestDto statusLoanRequestDto = new StatusLoanRequestDto(EstadoPrestamo.LIQUIDADO);
        LoanResponseDto loanResponseDtoUpdated = new LoanResponseDto(1,new BigDecimal("4000"), new BigDecimal("0.20"),14, new BigDecimal("400"), EstadoPrestamo.LIQUIDADO, 1 );
        when(prestamoService.updateStatusLoan(id, statusLoanRequestDto)).thenReturn(loanResponseDtoUpdated);
        mockMvc.perform(patch("/loans/"+id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(statusLoanRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loanId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(4000))
                .andExpect(jsonPath("$.interestRate").value(0.20))
                .andExpect(jsonPath("$.monthsOfDeadline").value(14))
                .andExpect(jsonPath("$.status").value("LIQUIDADO"));
    }

}
