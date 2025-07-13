import axios from 'axios';

const PACIENTES_API_URL = 'http://localhost:8900/api/pacientes';
const AUTH_API_URL = 'http://localhost:8900/api/auth';

export const registrarUsuario = async (datosRegistro) => {
  try {
    // Paso 1: Registrar paciente
    const pacienteData = {
      nombre: datosRegistro.nombre,
      apellidos: datosRegistro.apellidos,
      dni: datosRegistro.dni,
      distrito: datosRegistro.distrito,
      direccion: datosRegistro.direccion
    };

    const pacienteResponse = await axios.post(`${PACIENTES_API_URL}/registrar`, pacienteData);
    const pacienteId = pacienteResponse.data.id;

    // Paso 2: Registrar usuario en auth
    const authData = {
      email: datosRegistro.email,
      password: datosRegistro.password,
      rol: "PATIENT",
      entidadId: pacienteId
    };

    const authResponse = await axios.post(`${AUTH_API_URL}/register`, authData);

    return {
      success: true,
      data: authResponse.data,
      pacienteId: pacienteId
    };

  } catch (error) {
    throw new Error(error.response?.data?.message || 'Error al registrar usuario');
  }
};