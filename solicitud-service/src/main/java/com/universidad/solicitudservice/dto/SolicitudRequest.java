package com.universidad.solicitudservice.dto;

import javax.validation.constraints.NotBlank;

public class SolicitudRequest {
    @NotBlank(message = "Tipo de solicitud es requerido")
    private String tipoSolicitud;
    
    @NotBlank(message = "ID del estudiante es requerido")
    private String estudianteId;
    
    private String documento;
    private String observaciones;

    // Constructors
    public SolicitudRequest() {}

    public SolicitudRequest(String tipoSolicitud, String estudianteId, String documento, String observaciones) {
        this.tipoSolicitud = tipoSolicitud;
        this.estudianteId = estudianteId;
        this.documento = documento;
        this.observaciones = observaciones;
    }

    // Getters and Setters
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}