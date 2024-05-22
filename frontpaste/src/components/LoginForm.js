import React, { useState } from 'react';
import './LoginForm.css';

const LoginForm = ({ setMessage }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
    } catch (error) {
      setMessage('Произошла ошибка во время аутентификации.');
      console.error('Ошибка:', error);
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <h2>Вход</h2>
        <div className="input-group">
          <label htmlFor="username">Имя пользователя:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Пароль:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Войти</button>
      </form>
      <div className="auth-options">
        <p>Войти через:</p>
        <div className="social-login">
          <button className="google-login">Google</button>
          <button className="github-login">GitHub</button>
        </div>
      </div>
      <div className="register-link">
        <p>Нет аккаунта? <button>Зарегистрируйтесь</button></p>
      </div>
    </div>
  );
};

export default LoginForm;
