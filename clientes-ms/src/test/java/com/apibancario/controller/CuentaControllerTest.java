package com.apibancario.controller;

import com.apibancario.project_banca.model.dto.cliente.ClientResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountRequestDto;
import com.apibancario.project_banca.model.dto.cuenta.AccountResponseDto;
import com.apibancario.project_banca.model.dto.cuenta.PasswordRequestDto;
import com.apibancario.project_banca.model.enums.TipoCuenta;
import com.apibancario.project_banca.service.CuentaService;
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

@WebMvcTest(CuentaController.class)
public class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountResponseDto accountResponseDto;
    private AccountRequestDto accountRequestDto;
    private PasswordRequestDto passwordRequestDto;

    @BeforeEach
    void setUp() {
        ClientResponseDto clientResponseDto = new ClientResponseDto(1,"Elias", "Moran","elias@gmail.com");
        accountResponseDto = new AccountResponseDto(1,"56445587889696", TipoCuenta.AHORRO, clientResponseDto);
        accountRequestDto = new AccountRequestDto(TipoCuenta.AHORRO, "585898", new BigDecimal("40000.00"), 1);
        passwordRequestDto = new PasswordRequestDto("585898", "050505");
    }

    @Test
    void testGetAllAccounts200() throws Exception {
        when(cuentaService.listAll()).thenReturn(List.of(accountResponseDto));
        mockMvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].accountId").value(1))
                .andExpect(jsonPath("$[0].accountNumber").value("56445587889696"))
                .andExpect(jsonPath("$[0].accountType").value("AHORRO"));
    }

    @Test
    void testGetAccountById200() throws Exception{
        Integer id = 1;
        when(cuentaService.findById(id)).thenReturn(accountResponseDto);
        mockMvc.perform(get("/accounts/"+id).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.accountNumber").value("56445587889696"))
                .andExpect(jsonPath("$.accountType").value("AHORRO"));
    }

    @Test
    void testSaveAccount201() throws Exception{
        when(cuentaService.save(accountRequestDto)).thenReturn(accountResponseDto);
        mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.accountNumber").value("56445587889696"))
                .andExpect(jsonPath("$.accountType").value("AHORRO"));
    }

    @Test
    void testUpdatePasswordAccount200() throws Exception{
        Integer id = 1;
        when(cuentaService.updatePassword(id, passwordRequestDto)).thenReturn(accountResponseDto);
        mockMvc.perform(patch("/accounts/"+id+"/cambiar-clave").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(passwordRequestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1))
                .andExpect(jsonPath("$.accountNumber").value("56445587889696"))
                .andExpect(jsonPath("$.accountType").value("AHORRO"));
    }

    @Test
    void testDeleteAccount204() throws Exception{
        Integer id = 1;
        doNothing().when(cuentaService).delete(id);
        mockMvc.perform(delete("/accounts/"+id).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
