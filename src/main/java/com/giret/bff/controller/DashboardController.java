package com.giret.bff.controller;

import com.giret.bff.model.DashboardPanel;
import com.giret.bff.service.DashboardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/bff/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    DashboardServices dashboardServices;

    @GetMapping("/findAll")
    public ResponseEntity<DashboardPanel> findAllResources() {
        return ResponseEntity.ok(dashboardServices.getDashboardPanel());
    }

}
