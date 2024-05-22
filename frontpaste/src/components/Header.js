import React from 'react';
import './Header.css';
import { FaGithub } from 'react-icons/fa';
import logo from '../assets/logo.jpg';

const Header = () => {
  return (
    <header className="header">
      <div className="logo">
        <img src={logo} alt="Logo" />
      </div>
      <div className="github-icon">
        <a href="https://github.com/heiphin7/Pastebin-web-app" target="_blank" rel="noopener noreferrer">
          <FaGithub size={30} />
        </a>
      </div>
    </header>
  );
};

export default Header;
