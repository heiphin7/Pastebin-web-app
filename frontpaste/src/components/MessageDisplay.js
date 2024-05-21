import React from 'react';
import './MessageDisplay.css';

const MessageDisplay = ({ message }) => {
  return <div className="message-display">{message}</div>;
};

export default MessageDisplay;