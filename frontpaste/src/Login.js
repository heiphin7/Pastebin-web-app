import React, { useState } from 'react';
import { FaGoogle, FaGithub, FaUser } from 'react-icons/fa';
import { Link } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      setMessage('Successfully logged in');
    } catch (error) {
      setMessage('An error occurred during authentication.');
      console.error('Error:', error);
    }
  };

  const handleSocialLogin = (platform) => {
    console.log(`Login through ${platform}`);
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="icon-header">
          <FaUser size={50} />
        </div>
        <div className="input-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Login</button>
        {message && <p className="message">{message}</p>}
        <div className="or-divider">
          <div className="divider"></div>
          <p className="or-text">Or</p>
          <div className="divider"></div>
        </div>
        <div className="social-login">
          <button type="button" className="social-button google-login" onClick={() => handleSocialLogin('Google')}>
            <FaGoogle />
          </button>
          <button type="button" className="social-button github-login" onClick={() => handleSocialLogin('GitHub')}>
            <FaGithub />
          </button>
        </div>
        <div className="register-link">
          <p>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
      </form>
    </div>
  );
};

export default Login;
