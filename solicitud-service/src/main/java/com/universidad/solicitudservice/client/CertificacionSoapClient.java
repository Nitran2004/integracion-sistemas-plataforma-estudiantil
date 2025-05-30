package com.universidad.solicitudservice.client;

import com.universidad.solicitudservice.dto.SolicitudRequest;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CertificacionSoapClient {
    
    private final Random random = new Random();
    
    public String registrarCertificacion(SolicitudRequest request, String correlationId) {
        System.out.println("Registrando certificacion SOAP - CorrelationID: " + correlationId + 
                          ", Tipo: " + request.getTipoSolicitud());
        
        // Simular latencia y posibles fallos
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 segundos
            
            // Simular fallo ocasional (20% de probabilidad)
            if (random.nextInt(100) < 20) {
                throw new RuntimeException("Error simulado del servicio SOAP");
            }
            
            String resultado = "CERT_" + System.currentTimeMillis();
            System.out.println("Certificacion exitosa - CorrelationID: " + correlationId + 
                              ", Resultado: " + resultado);
            return resultado;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Servicio SOAP interrumpido", e);
        }
    }
}