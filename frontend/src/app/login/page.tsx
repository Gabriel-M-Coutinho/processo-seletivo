'use client';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './style.css';
import { useRouter } from 'next/navigation'; // Importe o useRouter

// Esquema simplificado apenas para login
const loginSchema = z.object({
  email: z.string().email('Email inválido'),
  password: z.string().min(6, 'Senha deve ter pelo menos 6 caracteres')
});

export default function LoginPage() {
  const router = useRouter(); // Use o hook de roteamento
  
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isDirty },
    trigger
  } = useForm({
    resolver: zodResolver(loginSchema),
    mode: 'onChange',
    reValidateMode: 'onChange'
  });

  const onSubmit = async (data: z.infer<typeof loginSchema>) => {
  try {
    const response = await fetch('http://localhost:8081/user/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: data.email,
        password: data.password
      }),
    });

    if (!response.ok) {
      const errorData = await response.json() as { message?: string };
      throw new Error(errorData.message || 'Erro ao fazer login');
    }

    const responseData = await response.json() as { token: string };
    
    if (typeof window !== 'undefined') {
      localStorage.setItem('token', responseData.token);
    }
    
    toast.success('Login realizado com sucesso!');
    setTimeout(() => router.push('/home'), 1000);
  } catch (error: unknown) {
    console.error('Erro:', error);
    
    let errorMessage = 'Erro ao conectar com o servidor';
    
    if (error instanceof Error) {
      errorMessage = error.message;
    } else if (typeof error === 'string') {
      errorMessage = error;
    } else if (error && typeof error === 'object' && 'message' in error) {
      errorMessage = String(error.message);
    }
    
    toast.error(errorMessage);
  }
};

  return (
    <div className="register-container">
      <ToastContainer position="top-right" autoClose={5000} />
     
      <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
        <h2>Login</h2>
       
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="seu@email.com"
            {...register('email', {
              onChange: () => trigger('email')
            })}
            className={errors.email ? 'error' : ''}
          />
          {errors.email && (
            <span className="error-message">{errors.email.message}</span>
          )}
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha</label>
          <input
            type="password"
            id="password"
            placeholder="Mínimo 6 caracteres"
            {...register('password', {
              onChange: () => trigger('password')
            })}
            className={errors.password ? 'error' : ''}
          />
          {errors.password && (
            <span className="error-message">{errors.password.message}</span>
          )}
        </div>
        <button
          type="submit"
          className="submit-btn"
          disabled={!isDirty || !isValid}
        >
          Entrar
        </button>
        <div className="login-link">
          Não tem uma conta? <a href="/register">Crie uma</a>
        </div>
      </form>
    </div>
  );
}