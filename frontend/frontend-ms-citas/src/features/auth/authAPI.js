import axios from 'axios';

const API_AUTH = "http://localhost:8900/api/auth/login";

export const loginUser = async (email, password) => {
    try {
        const response = await axios.post(API_AUTH, { email, password });

        if (response.data && response.data.token) {
            return response.data;
        } else {
            throw new Error("Respuesta del servidor inválida");
        }

    } catch (error) {
        console.error("Error de autenticación:", error.response?.data || error.message);

        if (error.response?.status === 401) {
            throw new Error("Credenciales incorrectas");
        } else if (error.response?.status === 500) {
            throw new Error("Error del servidor");
        } else if (!error.response) {
            throw new Error("No se puede conectar al servidor");
        }

        throw new Error(error.response?.data?.message || "Error al iniciar sesión");
    }
};

