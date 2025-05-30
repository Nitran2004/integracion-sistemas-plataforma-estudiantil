package com.universidad.solicitudservice.dto;

import java.time.LocalDateTime;

public class SolicitudResponse {
    private String id;
    private String tipoSolicitud;
    private String estudianteId;
    private String estado;
    private String resultadoCertificacion;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private String correlationId;

    // Constructor vacío
    public SolicitudResponse() {}

    // Constructor completo
    public SolicitudResponse(String id, String tipoSolicitud, String estudianteId, 
                           String estado, String resultadoCertificacion, String observaciones, 
                           LocalDateTime fechaCreacion, String correlationId) {
        this.id = id;
        this.tipoSolicitud = tipoSolicitud;
        this.estudianteId = estudianteId;
        this.estado = estado;
        this.resultadoCertificacion = resultadoCertificacion;
        this.observaciones = observaciones;
        this.fechaCreacion = fechaCreacion;
        this.correlationId = correlationId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(String estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResultadoCertificacion() {
        return resultadoCertificacion;
    }

    public void setResultadoCertificacion(String resultadoCertificacion) {
        this.resultadoCertificacion = resultadoCertificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}