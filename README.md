# Sistema de Gestión de Citas Médicas

## Arquitectura de Microservicios

### Servicios Disponibles:
- **Eureka Server** (8761) - Registro de servicios
- **Gateway** (8900) - API Gateway
- **ms-auth-service** (8080) - Autenticación y autorización
- **ms-patient-service** (8081) - Gestión de pacientes
- **ms-medico-service** (8083) - Gestión de médicos
- **ms-citas-service** (8082) - Gestión de citas médicas

## Endpoints Principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario

### Médicos
- `GET /api/medicos` - Listar médicos
- `GET /api/medicos/{id}` - Obtener médico por ID

### Citas (Requiere JWT)
- `POST /api/citas/reservar` - Reservar cita (PATIENT)
- `GET /api/citas/medico` - Ver citas del médico (DOCTOR)
- `GET /api/citas/paciente` - Ver citas del paciente (PATIENT)

## Inicio Rápido

### Opción 1: Docker Compose
```bash
docker-compose up -d
```

### Opción 2: Ejecución Local
```powershell
.\start-services.ps1
```

## URLs de Acceso
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8900
- **Todos los servicios via Gateway**: http://localhost:8900/api/*

## Autenticación JWT
El sistema usa JWT con roles:
- `PATIENT` - Para pacientes
- `DOCTOR` - Para médicos

El UUID del usuario se extrae del campo `sub` del token JWT.

## Base de Datos
- MySQL 8.0
- Bases de datos: `auth_db`, `patient_db`, `medico_db`, `citas_db`