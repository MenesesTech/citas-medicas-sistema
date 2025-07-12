-- Crear bases de datos para los microservicios
CREATE DATABASE IF NOT EXISTS auth_db;
CREATE DATABASE IF NOT EXISTS patient_db;
CREATE DATABASE IF NOT EXISTS medico_db;
CREATE DATABASE IF NOT EXISTS citas_db;

-- Otorgar permisos
GRANT ALL PRIVILEGES ON auth_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON patient_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON medico_db.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON citas_db.* TO 'root'@'%';
FLUSH PRIVILEGES;