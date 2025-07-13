import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { registrarUsuario } from "../../auth/RegistroService";
import "./registro.css";

export const Registro = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nombre: "",
    apellidos: "",
    email: "",
    password: "",
    dni: "",
    distrito: "",
    direccion: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    // Validaciones básicas
    if (
      !formData.nombre ||
      !formData.apellidos ||
      !formData.email ||
      !formData.password ||
      !formData.dni ||
      !formData.distrito ||
      !formData.direccion
    ) {
      setError("Por favor completa todos los campos");
      return;
    }

    if (formData.password.length < 6) {
      setError("La contraseña debe tener al menos 6 caracteres");
      return;
    }

    setLoading(true);
    try {
      await registrarUsuario(formData);
      setSuccess("Usuario registrado exitosamente");
      setTimeout(() => {
        navigate("/login");
      }, 2000);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="registro-fullscreen">
      <div className="container-fluid h-100">
        <div className="row h-100 g-0">
          {/* Sección Izquierda - Branding */}
          <div className="col-lg-6 d-flex align-items-center justify-content-center registro-brand-section">
            <div className="text-center text-white px-4">
              <div className="brand-icon mb-4">
                <i className="fas fa-user-plus fa-5x"></i>
              </div>
              <h1 className="display-4 fw-bold mb-3">Únete a Nosotros</h1>
              <p className="lead mb-0">
                Crea tu cuenta y accede a nuestros servicios médicos
              </p>
            </div>
          </div>

          {/* Sección Derecha - Formulario */}
          <div className="col-lg-6 d-flex align-items-center justify-content-center bg-light">
            <div className="registro-form-container">
              <div className="card shadow-lg border-0">
                <div className="card-body p-4">
                  <div className="text-center mb-4">
                    <div className="registro-avatar mb-3">
                      <i className="fas fa-user-edit fa-4x text-primary"></i>
                    </div>
                    <h2 className="card-title fw-bold text-dark">
                      Crear Cuenta
                    </h2>
                    <p className="text-muted">
                      Completa tus datos para registrarte
                    </p>
                  </div>

                  <form onSubmit={handleSubmit}>
                    <div className="row">
                      <div className="col-md-6 mb-3">
                        <div className="input-group">
                          <span className="input-group-text bg-primary text-white border-primary">
                            <i className="fas fa-user"></i>
                          </span>
                          <input
                            type="text"
                            name="nombre"
                            className="form-control border-primary"
                            placeholder="Nombre"
                            value={formData.nombre}
                            onChange={handleChange}
                            required
                          />
                        </div>
                      </div>
                      <div className="col-md-6 mb-3">
                        <div className="input-group">
                          <span className="input-group-text bg-primary text-white border-primary">
                            <i className="fas fa-user"></i>
                          </span>
                          <input
                            type="text"
                            name="apellidos"
                            className="form-control border-primary"
                            placeholder="Apellidos"
                            value={formData.apellidos}
                            onChange={handleChange}
                            required
                          />
                        </div>
                      </div>
                    </div>

                    <div className="mb-3">
                      <div className="input-group">
                        <span className="input-group-text bg-primary text-white border-primary">
                          <i className="fas fa-envelope"></i>
                        </span>
                        <input
                          type="email"
                          name="email"
                          className="form-control border-primary"
                          placeholder="Correo electrónico"
                          value={formData.email}
                          onChange={handleChange}
                          required
                        />
                      </div>
                    </div>

                    <div className="mb-3">
                      <div className="input-group">
                        <span className="input-group-text bg-primary text-white border-primary">
                          <i className="fas fa-lock"></i>
                        </span>
                        <input
                          type="password"
                          name="password"
                          className="form-control border-primary"
                          placeholder="Contraseña"
                          value={formData.password}
                          onChange={handleChange}
                          required
                        />
                      </div>
                    </div>

                    <div className="row">
                      <div className="col-md-6 mb-3">
                        <div className="input-group">
                          <span className="input-group-text bg-primary text-white border-primary">
                            <i className="fas fa-id-card"></i>
                          </span>
                          <input
                            type="text"
                            name="dni"
                            className="form-control border-primary"
                            placeholder="DNI"
                            value={formData.dni}
                            onChange={handleChange}
                            required
                          />
                        </div>
                      </div>
                      <div className="col-md-6 mb-3">
                        <div className="input-group">
                          <span className="input-group-text bg-primary text-white border-primary">
                            <i className="fas fa-map-marker-alt"></i>
                          </span>
                          <input
                            type="text"
                            name="distrito"
                            className="form-control border-primary"
                            placeholder="Distrito"
                            value={formData.distrito}
                            onChange={handleChange}
                            required
                          />
                        </div>
                      </div>
                    </div>

                    <div className="mb-4">
                      <div className="input-group">
                        <span className="input-group-text bg-primary text-white border-primary">
                          <i className="fas fa-home"></i>
                        </span>
                        <input
                          type="text"
                          name="direccion"
                          className="form-control border-primary"
                          placeholder="Dirección completa"
                          value={formData.direccion}
                          onChange={handleChange}
                          required
                        />
                      </div>
                    </div>

                    {error && (
                      <div
                        className="alert alert-danger d-flex align-items-center mb-3"
                        role="alert"
                      >
                        <i className="fas fa-exclamation-triangle me-2"></i>
                        <div>{error}</div>
                      </div>
                    )}

                    {success && (
                      <div
                        className="alert alert-success d-flex align-items-center mb-3"
                        role="alert"
                      >
                        <i className="fas fa-check-circle me-2"></i>
                        <div>{success}</div>
                      </div>
                    )}

                    <div className="d-grid mb-3">
                      <button
                        type="submit"
                        className="btn btn-primary btn-lg fw-bold"
                        disabled={loading}
                      >
                        {loading ? (
                          <>
                            <span
                              className="spinner-border spinner-border-sm me-2"
                              role="status"
                              aria-hidden="true"
                            ></span>
                            Registrando...
                          </>
                        ) : (
                          <>
                            <i className="fas fa-user-plus me-2"></i>
                            Crear Cuenta
                          </>
                        )}
                      </button>
                    </div>
                  </form>

                  <div className="text-center">
                    <p className="mb-0">
                      ¿Ya tienes cuenta?{" "}
                      <Link
                        to="/login"
                        className="text-primary fw-bold text-decoration-none"
                      >
                        Inicia sesión aquí
                      </Link>
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
