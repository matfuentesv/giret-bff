package com.giret.bff.service.impl;

import com.giret.bff.client.FunctionClient;
import com.giret.bff.client.LoanClient;
import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;
import com.giret.bff.service.LoanServices;
import com.giret.bff.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LoanServicesImpl implements LoanServices {

    @Autowired
    LoanClient loanClient;

    @Autowired
    ResourceService resourceService;

    @Autowired
    FunctionClient functionClient;

    @Override
    public List<Loan> getAllLoans() {
        return loanClient.findAllLoan().stream()
                .map(loan -> Loan.builder()
                        .idPrestamo(loan.getIdPrestamo())
                        .recursoId(loan.getRecursoId())
                        .fechaPrestamo(loan.getFechaPrestamo())
                        .fechaDevolucion(loan.getFechaDevolucion())
                        .solicitante(loan.getSolicitante())
                        .estado(loan.getEstado())
                        .resource(resourceService.findResourceById(loan.getRecursoId()))
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public List<Loan> getLoansByResourceId(Long resourceId) {
        return loanClient.findLoandByResource(resourceId);
    }



    @Override
    public Loan saveLoan(Loan loan) {
        //Llamar function recurso ===> estado(prestado)
        final Resource resource = resourceService.findResourceById(loan.getRecursoId());
        functionClient.updateResource(resource);
        return loanClient.saveLoan(loan);
    }

    @Override
    public Loan updateLoanByState(String state, Long id) {

        return loanClient.updateLoanByState(state, id);
    }
}
