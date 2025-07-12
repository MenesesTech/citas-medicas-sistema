# Script para iniciar los microservicios en orden
Write-Host "Iniciando sistema de microservicios..." -ForegroundColor Green

# 1. Iniciar Eureka Server
Write-Host "1. Iniciando Eureka Server..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\eureka-server'; .\mvnw.cmd spring-boot:run"

# Esperar a que Eureka esté listo
Write-Host "Esperando a que Eureka Server esté listo..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 2. Iniciar microservicios
Write-Host "2. Iniciando ms-auth-service..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\ms-auth-service'; .\mvnw.cmd spring-boot:run"

Write-Host "3. Iniciando ms-patient-service..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\ms-patient-service'; .\mvnw.cmd spring-boot:run"

Write-Host "4. Iniciando ms-medico-service..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\ms-medico-service'; .\env.ps1; .\mvnw.cmd spring-boot:run"

Write-Host "5. Iniciando ms-citas-service..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\ms-citas-service'; .\env.ps1; .\mvnw.cmd spring-boot:run"

# Esperar a que los microservicios se registren
Write-Host "Esperando a que los microservicios se registren..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 6. Iniciar Gateway
Write-Host "6. Iniciando Gateway..." -ForegroundColor Yellow
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd 'c:\DevProjects\citas-medicas-sistema\gateway'; .\mvnw.cmd spring-boot:run"

Write-Host "Sistema iniciado. URLs disponibles:" -ForegroundColor Green
Write-Host "- Eureka Server: http://localhost:8761" -ForegroundColor Cyan
Write-Host "- Gateway: http://localhost:8900" -ForegroundColor Cyan
Write-Host "- Auth Service (via Gateway): http://localhost:8900/api/auth" -ForegroundColor Cyan
Write-Host "- Patient Service (via Gateway): http://localhost:8900/api/patients" -ForegroundColor Cyan
Write-Host "- Medico Service (via Gateway): http://localhost:8900/api/medicos" -ForegroundColor Cyan
Write-Host "- Citas Service (via Gateway): http://localhost:8900/api/citas" -ForegroundColor Cyan