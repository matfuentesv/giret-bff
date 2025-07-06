package com.giret.bff.service.impl;

import com.giret.bff.client.FunctionClient;
import com.giret.bff.client.HistoricalResourceClient;
import com.giret.bff.client.LoanClient;
import com.giret.bff.model.HistoricalResource;
import com.giret.bff.model.Loan;
import com.giret.bff.model.Resource;
import com.giret.bff.model.UpdateLoan;
import com.giret.bff.service.LoanServices;
import com.giret.bff.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    @Value("${azure.function.updateResource.key}")
    private String functionKeyResource;

    @Value("${azure.function.updateLoan.key}")
    private String functionKeyLoan;

    @Autowired
    HistoricalResourceClient historicalResourceClient;

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
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId chileZoneId = ZoneId.of("America/Santiago");
        String fechaCambioEstado = ZonedDateTime.now(chileZoneId).format(formatter);
        final Resource resource = resourceService.findResourceById(loan.getRecursoId());
        functionClient.updateResource(functionKeyResource,resource);
        final HistoricalResource historicalResource = HistoricalResource
                .builder()
                .recursoId(resource.getIdRecurso())
                .fechaCambioEstado(fechaCambioEstado)
                .accion("Actualizacion")
                .descripcion("Cambio de estado a prestado")
                .build();
        historicalResourceClient.saveHistoricalResource(historicalResource);
        return loanClient.saveLoan(loan);
    }

    @Override
    public Boolean updateLoanByState(UpdateLoan body) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final ZoneId chileZoneId = ZoneId.of("America/Santiago");
        String fechaCambioEstado = ZonedDateTime.now(chileZoneId).format(formatter);
        functionClient.updateLoan(functionKeyLoan,body);
        final HistoricalResource historicalResource = HistoricalResource
                .builder()
                .recursoId(body.getRecursoId())
                .fechaCambioEstado(fechaCambioEstado)
                .accion("Actualizacion")
                .descripcion("Cambio de estado a devuelto a Bodega")
                .build();
        historicalResourceClient.saveHistoricalResource(historicalResource);
        return true;
    }
}
