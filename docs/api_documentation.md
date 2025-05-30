# 📚 Documentación de API - Solicitud Service

## Base URL
```
Directo: http://localhost:8085
Kong Gateway: http://localhost:8000
```

---

## 🔐 Autenticación

Todos los endpoints requieren un token JWT válido en el header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

### Generar Token JWT
```bash
python jwt-generator.py
```

---

## 📋 Endpoints

### 1. Health Check

**GET** `/health`

Verifica el estado del servicio.

**Headers:**
```http
Content-Type: application/json
```

**Response:**
```json
{
  "status": "UP",
  "timestamp": "2025-05-29T19:30:45.123",
  "service": "solicitud-service",
  "version": "1.0.0"
}
```

---

### 2. Crear Solicitud

**POST** `/solicitudes`

Crea una nueva solicitud de certificado académico.

**Headers:**
```http
Authorization: Bearer {jwt_token}
Content-Type: application/json
X-Correlation-ID: {correlation_id}
```

**Request Body:**
```json
{
  "tipoSolicitud": "CERTIFICADO_NOTAS",
  "estudianteId": "EST001",
  "documento": "cedula-123456789.pdf",
  "observaciones": "Certificado requerido para trámite laboral"
}
```

**Response (201 Created):**
```json
{
  "id": "bf68d8d5-444b-4253-bc99-4e13bd728e15",
  "tipoSolicitud": "CERTIFICADO_NOTAS",
  "estudianteId": "EST001",
  "estado": "PROCESADO",
  "resultadoCertificacion": "CERT_1748549070061",
  "observaciones": null,
  "fechaCreacion": "2025-05-29T19:31:10.061",
  "correlationId": "test-20250529193110"
}
```

**Códigos de Estado:**
- `201 Created` - Solicitud creada exitosamente
- `400 Bad Request` - Datos inválidos en el request
- `401 Unauthorized` - Token JWT inválido o faltante
- `500 Internal Server Error` - Error interno del servidor

---

### 3. Consultar Solicitud

**GET** `/solicitudes/{id}`

Obtiene los detalles de una solicitud específica.

**Headers:**
```http
Authorization: Bearer {jwt_token}
X-Correlation-ID: {correlation_id}
```

**Path Parameters:**
- `id` (string, required) - UUID de la solicitud

**Response (200 OK):**
```json
{
  "id": "bf68d8d5-444b-4253-bc99-4e13bd728e15",
  "tipoSolicitud": "CERTIFICADO_NOTAS",
  "estudianteId": "EST001",
  "estado": "PROCESADO",
  "resultadoCertificacion": "CERT_1748549070061",
  "observaciones": null,
  "fechaCreacion": "2025-05-29T19:31:10.061",
  "correlationId": "test-20250529193110"
}
```

**Códigos de Estado:**
- `200 OK` - Solicitud encontrada
- `401 Unauthorized` - Token JWT inválido o faltante
- `404 Not Found` - Solicitud no encontrada
- `500 Internal Server Error` - Error interno del servidor

---

## 📊 Modelos de Datos

### SolicitudRequest
```json
{
  "tipoSolicitud": "string", // Requerido: Tipo de certificado solicitado
  "estudianteId": "string",  // Requerido: ID del estudiante
  "documento": "string",     // Opcional: Nombre del documento adjunto
  "observaciones": "string"  // Opcional: Comentarios adicionales
}
```

### SolicitudResponse  
```json
{
  "id": "string",                    // UUID único de la solicitud
  "tipoSolicitud": "string",         // Tipo de certificado
  "estudianteId": "string",          // ID del estudiante
  "estado": "string",                // PROCESADO | EN_REVISION | EN_PROCESO
  "resultadoCertificacion": "string", // ID del certificado generado
  "observaciones": "string",         // Comentarios del proceso
  "fechaCreacion": "datetime",       // Timestamp de creación ISO 8601
  "correlationId": "string"          // ID de correlación para trazabilidad
}
```

---

## 🔧 Estados de Solicitud

| Estado | Descripción |
|--------|-------------|
| `EN_PROCESO` | Solicitud recibida, en procesamiento |
| `PROCESADO` | Solicitud completada exitosamente |
| `EN_REVISION` | Error en certificación, requiere revisión manual |

---

## 🧪 Ejemplos de Testing

### PowerShell Examples

**1. Generar Token:**
```powershell
python jwt-generator.py
$JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

**2. Health Check:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8085/health" -Method Get
```

**3. Crear Solicitud:**
```powershell
$headers = @{
    "Authorization" = "Bearer $JWT_TOKEN"
    "Content-Type" = "application/json"
    "X-Correlation-ID" = "test-$(Get-Date -Format 'yyyyMMddHHmmss')"
}

$body = @{
    tipoSolicitud = "CERTIFICADO_NOTAS"
    estudianteId = "EST001"
    documento = "cedula-123456789.pdf"
    observaciones = "Certificado para trámite laboral"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8085/solicitudes" -Method Post -Headers $headers -Body $body
```

**4. Consultar Solicitud:**
```powershell
$solicitudId = $response.id
Invoke-RestMethod -Uri "http://localhost:8085/solicitudes/$solicitudId" -Method Get -Headers $headers
```

### curl Examples

**1. Health Check:**
```bash
curl http://localhost:8085/health
```

**2. Crear Solicitud:**
```bash
curl -X POST http://localhost:8085/solicitudes \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -H "X-Correlation-ID: test-001" \
  -d '{
    "tipoSolicitud": "CERTIFICADO_NOTAS",
    "estudianteId": "EST001",
    "documento": "cedula-123456789.pdf",
    "observaciones": "Certificado para trámite laboral"
  }'
```

**3. Consultar Solicitud:**
```bash
curl -X GET http://localhost:8085/solicitudes/{id} \
  -H "Authorization: Bearer $JWT_TOKEN" \
  -H "X-Correlation-ID: test-002"
```

---

## 🌐 Acceso vía Kong Gateway

Para usar el API Gateway en lugar del acceso directo:

**Base URL:** `http://localhost:8000`

**Ejemplo:**
```powershell
# En lugar de localhost:8085, usar localhost:8000
Invoke-RestMethod -Uri "http://localhost:8000/solicitudes" -Method Post -Headers $headers -Body $body
```

---

## ⚠️ Manejo de Errores

### Errores Comunes

**401 Unauthorized:**
```json
{
  "timestamp": "2025-05-29T19:30:45.123Z",
  "status": 401,
  "error": "Unauthorized",
  "message": "Token JWT inválido",
  "path": "/solicitudes"
}
```

**400 Bad Request:**
```json
{
  "timestamp": "2025-05-29T19:30:45.123Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Tipo de solicitud es requerido",
  "path": "/solicitudes"
}
```

**404 Not Found:**
```json
{
  "timestamp": "2025-05-29T19:30:45.123Z",
  "status": 404,
  "error": "Not Found",
  "message": "Solicitud no encontrada",
  "path": "/solicitudes/invalid-id"
}
```

---

## 📈 Monitoreo y Observabilidad

### Headers de Trazabilidad
- `X-Correlation-ID`: ID único para seguimiento de requests
- Los logs internos incluyen este ID para trazabilidad completa

### Métricas Disponibles
- **Prometheus**: http://localhost:9090
- **Métricas del servicio**: http://localhost:8085/actuator/prometheus

### Trazas Distribuidas
- **Zipkin**: http://localhost:9411
- Cada request genera trazas automáticamente

---

*Documentación generada para el proyecto de Integración de Sistemas - 2025*