import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../../../context/AuthContext";
import { loginUser } from "../../auth/authAPI";
import "./login.css";

export const Login = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    if (!email || !password) {
      setError("Por favor completa todos los campos");
      return;
    }

    setLoading(true);
    try {
      const response = await loginUser(email, password);
      const { token, user } = response;
      login(token, user);
      navigate("/");
    } catch (error) {
      setError(error.message || "Error al iniciar sesión");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-fullscreen">
      <div className="container-fluid h-100">
        <div className="row h-100 g-0">
          {/* Sección Izquierda - Branding */}
          <div className="col-lg-6 d-flex align-items-center justify-content-center login-brand-section">
            <div className="text-center text-white px-4">
              <div className="brand-icon mb-4">
                <i className="fas fa-user-md fa-5x"></i>
              </div>
              <h1 className="display-4 fw-bold mb-3">Sistema de Citas Médicas</h1>
              <p className="lead mb-0">Plataforma integral para la gestión de citas médicas profesional</p>
            </div>
          </div>

          {/* Sección Derecha - Formulario */}
          <div className="col-lg-6 d-flex align-items-center justify-content-center bg-light">
            <div className="login-form-container">
              <div className="card shadow-lg border-0">
                <div className="card-body p-5">
                  <div className="text-center mb-4">
                    <div className="login-avatar mb-3">
                      <i className="fas fa-user-circle fa-4x text-success"></i>
                    </div>
                    <h2 className="card-title fw-bold text-dark">Iniciar Sesión</h2>
                    <p className="text-muted">Accede a tu cuenta para continuar</p>
                  </div>

                  <form onSubmit={handleLogin}>
                    <div className="mb-4">
                      <div className="input-group input-group-lg">
                        <span className="input-group-text bg-success text-white border-success">
                          <i className="fas fa-envelope"></i>
                        </span>
                        <input
                          type="email"
                          className="form-control border-success"
                          placeholder="Correo electrónico"
                          value={email}
                          onChange={(e) => setEmail(e.target.value)}
                          required
                        />
                      </div>
                    </div>

                    <div className="mb-4">
                      <div className="input-group input-group-lg">
                        <span className="input-group-text bg-success text-white border-success">
                          <i className="fas fa-lock"></i>
                        </span>
                        <input
                          type="password"
                          className="form-control border-success"
                          placeholder="Contraseña"
                          value={password}
                          onChange={(e) => setPassword(e.target.value)}
                          required
                        />
                      </div>
                    </div>

                    {error && (
                      <div className="alert alert-danger d-flex align-items-center mb-4" role="alert">
                        <i className="fas fa-exclamation-triangle me-2"></i>
                        <div>{error}</div>
                      </div>
                    )}

                    <div className="d-grid mb-4">
                      <button
                        type="submit"
                        className="btn btn-success btn-lg fw-bold"
                        disabled={loading}
                      >
                        {loading ? (
                          <>
                            <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                            Iniciando...
                          </>
                        ) : (
                          <>
                            <i className="fas fa-sign-in-alt me-2"></i>
                            Iniciar Sesión
                          </>
                        )}
                      </button>
                    </div>
                  </form>

                  <div className="text-center">
                    <p className="mb-0">
                      ¿No tienes cuenta?{" "}
                      <Link to="/registro-user" className="text-success fw-bold text-decoration-none">
                        Regístrate aquí
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