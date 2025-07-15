package com.giret.bff.service;

import com.giret.bff.client.FunctionClient;
import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.client.LoanClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;
import com.giret.bff.model.UpdateLoan;
import com.giret.bff.service.impl.LoanServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoanServicesImplTest {

    private LoanClient loanClient;
    private ResourceService resourceService;
    private FunctionClient functionClient;
    private HistoricalResourceClient historicalResourceClient;
    private LoanServicesImpl loanServices;

    @BeforeEach
    void setUp() {
        loanClient = mock(LoanClient.class);
        resourceService = mock(ResourceService.class);
        functionClient = mock(FunctionClient.class);
        historicalResourceClient = mock(HistoricalResourceClient.class);

        loanServices = new LoanServicesImpl(
                loanClient,
                resourceService,
                functionClient,
                historicalResourceClient
        );


        loanServices.functionKeyResource = "dummyResourceKey";
        loanServices.functionKeyLoan = "dummyLoanKey";
    }

    @Test
    void testGetAllLoans() {
        Loan loan = Loan.builder()
                .idPrestamo(1L)
                .recursoId(100L)
                .estado("activo")
                .fechaPrestamo("2025-07-15")
                .fechaDevolucion("2025-07-20")
                .solicitante("Juan")
                .build();

        Resource resource = Resource.builder()
                .idRecurso(100L)
                .modelo("PC")
                .build();

        when(loanClient.findAllLoan()).thenReturn(List.of(loan));
        when(resourceService.findResourceById(100L)).thenReturn(resource);

        List<Loan> result = loanServices.getAllLoans();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getResource().getIdRecurso());

        verify(loanClient, times(1)).findAllLoan();
        verify(resourceService, times(1)).findResourceById(100L);
    }

    @Test
    void testGetLoansByResourceId() {
        Loan loan = Loan.builder().idPrestamo(2L).recursoId(200L).build();
        when(loanClient.findLoandByResource(200L)).thenReturn(List.of(loan));

        List<Loan> result = loanServices.getLoansByResourceId(200L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getIdPrestamo());

        verify(loanClient, times(1)).findLoandByResource(200L);
    }

    @Test
    void testSaveLoan() {
        Loan loan = Loan.builder().idPrestamo(3L).recursoId(300L).build();
        Resource resource = Resource.builder().idRecurso(300L).estado("activo").build();

        when(resourceService.findResourceById(300L)).thenReturn(resource);
        when(loanClient.saveLoan(loan)).thenReturn(loan);

        Loan result = loanServices.saveLoan(loan);

        assertNotNull(result);
        assertEquals(3L, result.getIdPrestamo());

        verify(resourceService, times(1)).findResourceById(300L);
        verify(functionClient, times(1)).updateResource(anyString(), eq(resource));
        verify(historicalResourceClient, times(1)).saveHistoricalResource(any(HistoricalResource.class));
        verify(loanClient, times(1)).saveLoan(loan);
    }

    @Test
    void testUpdateLoanByState() {
        UpdateLoan body = UpdateLoan.builder()
                .prestamoId(4L)
                .recursoId(400L)
                .build();

        Boolean result = loanServices.updateLoanByState(body);

        assertTrue(result);

        verify(functionClient, times(1)).updateLoan(anyString(), eq(body));
        verify(historicalResourceClient, times(1)).saveHistoricalResource(any(HistoricalResource.class));
    }
}
