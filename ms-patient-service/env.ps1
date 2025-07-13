# ===================================
#   CONFIGURACION DE BASE DE DATOS
# ===================================
$env:DB_URL = "jdbc:mysql://localhost:3306/paciente_clinic_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "1234"
$env:DB_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver"

# ===================================
#   CONFIGURACION DE SEGURIDAD JWT
# ===================================
# $env:JWT_SECRET = "a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7r8s9t0u1v2w3x4y5z6a7b8c9d0e1f2"
# $env:JWT_EXPIRATION = "3600000"

# API Key para administración
# $env:ADMIN_API_KEY = "96rFZ8ff2a4oYzMna3uiL2"

# ===================================
#       CONFIGURACION DE SERVIDOR
# ===================================
$env:SERVER_PORT = "8080"
$env:DDL_AUTO = "update"

# ===================================
#       CONFIGURACION DE CORS
# ===================================
$env:CORS_ALLOWED_ORIGINS = "http://localhost:5173"

# ===================================
#       CONFIGURACION DE LOGGING
# ===================================
$env:LOG_LEVEL = "INFO"
$env:APP_LOG_LEVEL = "DEBUG"
$env:SHOW_SQL = "false"
$env:FORMAT_SQL = "false"

# ===================================
#   CONFIGURACION DE RATE LIMITING
# ===================================
$env:RATE_LIMIT_REQUESTS_PER_MINUTE = "60"
$env:RATE_LIMIT_BURST_SIZE = "10"

# ===================================
#   CONFIGURACION DE ENTORNO
# ===================================
$env:SPRING_PROFILES_ACTIVE = "production"
$env:ENVIRONMENT = "production"
