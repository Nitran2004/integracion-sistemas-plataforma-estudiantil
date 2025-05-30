package com.universidad.solicitudservice.controller;

import com.universidad.solicitudservice.dto.SolicitudRequest;
import com.universidad.solicitudservice.dto.SolicitudResponse;
import com.universidad.solicitudservice.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {
    
    @Autowired
    private SolicitudService solicitudService;
    
    @PostMapping
    public ResponseEntity<SolicitudResponse> crearSolicitud(
            @Valid @RequestBody SolicitudRequest request,
            HttpServletRequest httpRequest) {
        
        String correlationId = httpRequest.getHeader("X-Correlation-ID");
        String authHeader = httpRequest.getHeader("Authorization");
        
        System.out.println("Procesando solicitud - CorrelationID: " + correlationId);
        
        try {
            SolicitudResponse response = solicitudService.procesarSolicitud(request, authHeader, correlationId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error procesando solicitud - CorrelationID: " + correlationId + ", Error: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> obtenerSolicitud(
            @PathVariable String id,
            HttpServletRequest httpRequest) {
        
        String correlationId = httpRequest.getHeader("X-Correlation-ID");
        String authHeader = httpRequest.getHeader("Authorization");
        
        System.out.println("Consultando solicitud " + id + " - CorrelationID: " + correlationId);
        
        try {
            SolicitudResponse response = solicitudService.obtenerSolicitud(id, authHeader, correlationId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error consultando solicitud " + id + " - CorrelationID: " + correlationId + ", Error: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}