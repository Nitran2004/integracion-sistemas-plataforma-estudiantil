package com.universidad.solicitudservice.service;

import com.universidad.solicitudservice.dto.SolicitudRequest;
import com.universidad.solicitudservice.dto.SolicitudResponse;
import com.universidad.solicitudservice.client.CertificacionSoapClient;
import com.universidad.solicitudservice.security.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SolicitudService {
    
    private final ConcurrentHashMap<String, SolicitudResponse> solicitudesDB = new ConcurrentHashMap<>();
    
    @Autowired
    private CertificacionSoapClient certificacionClient;
    
    @Autowired
    private JwtValidator jwtValidator;
    
    public SolicitudResponse procesarSolicitud(SolicitudRequest request, String authHeader, String correlationId) {
        // Validar JWT
        if (!jwtValidator.validarToken(authHeader)) {
            throw new SecurityException("Token JWT invalido");
        }
        
        String solicitudId = UUID.randomUUID().toString();
        
        SolicitudResponse response = new SolicitudResponse();
        response.setId(solicitudId);
        response.setTipoSolicitud(request.getTipoSolicitud());
        response.setEstudianteId(request.getEstudianteId());
        response.setEstado("EN_PROCESO");
        response.setFechaCreacion(LocalDateTime.now());
        response.setCorrelationId(correlationId);
        
        // Guardar en "base de datos" simulada
        solicitudesDB.put(solicitudId, response);
        
        // Llamar al servicio SOAP (sin Circuit Breaker temporalmente)
        try {
            String resultadoCertificacion = certificacionClient.registrarCertificacion(request, correlationId);
            response.setEstado("PROCESADO");
            response.setResultadoCertificacion(resultadoCertificacion);
        } catch (Exception e) {
            System.out.println("Error en certificacion - CorrelationID: " + correlationId + ", Error: " + e.getMessage());
            response.setEstado("EN_REVISION");
            response.setObservaciones("Error en proceso de certificacion, requiere revision manual");
        }
        
        // Actualizar en "base de datos"
        solicitudesDB.put(solicitudId, response);
        
        return response;
    }
    
    public SolicitudResponse obtenerSolicitud(String id, String authHeader, String correlationId) {
        // Validar JWT
        if (!jwtValidator.validarToken(authHeader)) {
            throw new SecurityException("Token JWT invalido");
        }
        
        SolicitudResponse response = solicitudesDB.get(id);
        if (response == null) {
            throw new RuntimeException("Solicitud no encontrada");
        }
        
        return response;
    }
}