import React, { useState } from 'react';
import Header from './components/Header';
import LoginForm from './components/LoginForm';
import MessageDisplay from './components/MessageDisplay';
import './App.css';

function App() {
  const [message, setMessage] = useState('');

  return (
    <div className="App">
      <Header />
      <LoginForm setMessage={setMessage} />
      <MessageDisplay message={message} />
    </div>
  );
}

export default App;