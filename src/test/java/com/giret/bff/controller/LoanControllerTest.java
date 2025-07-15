package com.giret.bff.controller;

import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;
import com.giret.bff.model.UpdateLoan;
import com.giret.bff.service.LoanServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoanControllerTest {

    private LoanServices loanServices;
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        loanServices = mock(LoanServices.class);
        loanController = new LoanController(loanServices);
    }

    @Test
    void testFindAllLoan() {
        Loan loan = Loan.builder()
                .idPrestamo(1L)
                .recursoId(2L)
                .estado("activo")
                .resource(Resource.builder().idRecurso(2L).build())
                .build();

        when(loanServices.getAllLoans()).thenReturn(List.of(loan));

        ResponseEntity<List<Loan>> response = loanController.findAllLoan();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getIdPrestamo());
        verify(loanServices, times(1)).getAllLoans();
    }

    @Test
    void testFindLoandByResource() {
        Loan loan = Loan.builder()
                .idPrestamo(2L)
                .recursoId(5L)
                .estado("devuelto")
                .build();

        when(loanServices.getLoansByResourceId(5L)).thenReturn(List.of(loan));

        ResponseEntity<List<Loan>> response = loanController.findLoandByResource(5L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(5L, response.getBody().get(0).getRecursoId());
        verify(loanServices, times(1)).getLoansByResourceId(5L);
    }

    @Test
    void testSaveLoan() {
        Loan request = Loan.builder()
                .recursoId(3L)
                .estado("prestado")
                .build();

        Loan saved = Loan.builder()
                .idPrestamo(3L)
                .recursoId(3L)
                .estado("prestado")
                .build();

        when(loanServices.saveLoan(request)).thenReturn(saved);

        ResponseEntity<Loan> response = loanController.saveLoan(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3L, response.getBody().getIdPrestamo());
        verify(loanServices, times(1)).saveLoan(request);
    }

    @Test
    void testUpdateLoanByState() {
        UpdateLoan updateLoan = UpdateLoan.builder()
                .prestamoId(4L)
                .recursoId(7L)
                .build();

        when(loanServices.updateLoanByState(updateLoan)).thenReturn(true);

        ResponseEntity<Boolean> response = loanController.updateLoanByState(updateLoan);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(Boolean.TRUE, response.getBody());
        verify(loanServices, times(1)).updateLoanByState(updateLoan);
    }

}
