"use client";
import { useEffect, useState } from 'react';
import ProtectedRoute from "../components/protectRoute";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './style.css';

type UserData = {
  id: string;
  name: string;
  email: string;
};

export default function HomePage() {
  const [userData, setUserData] = useState<UserData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('Token não encontrado');
        }

        const response = await fetch('http://localhost:8081/user/me', {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json'
          }
        });

        if (!response.ok) {
          throw new Error('Erro ao obter dados do usuário');
        }

        const data = await response.json();
        setUserData(data);
      } catch (err) {
        const message = err instanceof Error ? err.message : 'Erro desconhecido';
        setError(message);
        toast.error(message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/login';
  };

  return (
    <ProtectedRoute>
      <div className="home-container">
        <ToastContainer position="top-right" autoClose={5000} />
        
        <div className="dashboard-card">
          {loading ? (
            <div className="loading-spinner">
              <div className="spinner"></div>
              <p>Carregando seus dados...</p>
            </div>
          ) : error ? (
            <div className="error-card">
              <h3>Ocorreu um erro</h3>
              <p>{error}</p>
              <button 
                className="submit-btn"
                onClick={() => window.location.reload()}
              >
                Tentar novamente
              </button>
            </div>
          ) : userData ? (
            <>
              <div className="user-header">
                <h2>Olá, {userData.name}!</h2>
                <p>Bem-vindo à sua área exclusiva</p>
              </div>
              
              <div className="user-info">
                <div className="info-row">
                  <span className="info-label">Email:</span>
                  <span className="info-value">{userData.email}</span>
                </div>
                
                <div className="info-row">
                  <span className="info-label">ID:</span>
                  <span className="info-value">{userData.id}</span>
                </div>
              </div>
              
              <button 
                className="submit-btn logout-btn"
                onClick={handleLogout}
              >
                Sair da conta
              </button>
            </>
          ) : (
            <div className="empty-state">
              <p>Nenhum dado de usuário disponível</p>
            </div>
          )}
        </div>
      </div>
    </ProtectedRoute>
  );
}