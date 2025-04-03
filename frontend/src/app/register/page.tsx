'use client';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './style.css';
import { useRouter } from 'next/navigation';

const registerSchema = z.object({
  name: z.string().min(3, 'Nome deve ter pelo menos 3 caracteres'),
  email: z.string().email('Email inválido'),
  password: z.string().min(6, 'Senha deve ter pelo menos 6 caracteres'),
  confirmPassword: z.string()
}).refine(data => data.password === data.confirmPassword, {
  message: 'Senhas não coincidem',
  path: ['confirmPassword']
});

export default function RegisterPage() {
  const router = useRouter();
  const {
    register,
    handleSubmit,
    formState: { errors, isValid, isDirty },
    watch,
    trigger
  } = useForm({
    resolver: zodResolver(registerSchema),
    mode: 'onChange',
    reValidateMode: 'onChange'
  });

  const onSubmit = async (data: z.infer<typeof registerSchema>) => {
    try {
      const response = await fetch('http://localhost:8081/user/signin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: data.email,
          password: data.password,
          name: data.name
        })
      });

      if (!response.ok) {
        throw new Error('Erro ao cadastrar usuário');
      }

      const result = await response.json();
      console.log('Usuário cadastrado:', result);
      
      toast.success('Cadastro realizado com sucesso! Redirecionando para login...');
     
      setTimeout(() => {
        router.push('/login');
      }, 4000);

    } catch (error) {
      console.error('Erro no cadastro:', error);
      toast.error('Erro ao cadastrar. Por favor, tente novamente.');
    }
  };

  const password = watch('password');
  const confirmPassword = watch('confirmPassword');

  return (
    <div className="register-container">
      <ToastContainer position="top-right" autoClose={5000} />
      
      <form className="register-form" onSubmit={handleSubmit(onSubmit)}>
        <h2>Criar nova conta</h2>
        
        <div className="form-group">
          <label htmlFor="name">Nome completo</label>
          <input 
            type="text" 
            id="name" 
            placeholder="Digite seu nome"
            {...register('name', {
              onChange: () => trigger('name')
            })}
            className={errors.name ? 'error' : ''}
          />
          {errors.name && (
            <span className="error-message">{errors.name.message}</span>
          )}
        </div>

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
              onChange: () => {
                trigger('password');
                if (confirmPassword) trigger('confirmPassword');
              }
            })}
            className={errors.password ? 'error' : ''}
          />
          {errors.password && (
            <span className="error-message">{errors.password.message}</span>
          )}
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Confirme sua senha</label>
          <input 
            type="password" 
            id="confirmPassword" 
            placeholder="Digite novamente sua senha"
            {...register('confirmPassword', {
              onChange: () => trigger('confirmPassword')
            })}
            className={errors.confirmPassword ? 'error' : ''}
          />
          {errors.confirmPassword && (
            <span className="error-message">{errors.confirmPassword.message}</span>
          )}
        </div>

        <button 
          type="submit" 
          className="submit-btn"
          disabled={!isDirty || !isValid}
        >
          Registrar
        </button>

        <div className="login-link">
          Já tem uma conta? <a href="/login">Faça login</a>
        </div>
      </form>
    </div>
  );
}