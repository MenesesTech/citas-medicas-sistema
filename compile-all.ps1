# Script para compilar todos los microservicios
Write-Host "Compilando todos los microservicios..." -ForegroundColor Green

$services = @(
  "ms-auth-service",
  "ms-patient-service", 
  "ms-citas-service",
  "ms-medico-service",
  "gateway",
  "eureka-server"
)

foreach ($service in $services) {
  Write-Host "Compilando $service..." -ForegroundColor Yellow
  Set-Location $service
  .\mvnw.cmd clean package -DskipTests
  if ($LASTEXITCODE -ne 0) {
    Write-Host "Error compilando $service" -ForegroundColor Red
    exit 1
  }
  Set-Location ..
}

Write-Host "Compilación completada exitosamente!" -ForegroundColor Green