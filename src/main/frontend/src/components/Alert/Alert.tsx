import React, { FC } from 'react';
import './Alert.css';

interface AlertProps {
  message: string,
  showIcon: boolean,
  type: "SUCCESS" | "ERROR" | "WARNING" | "INFO"
}

const Alert: FC<AlertProps> = ({ message, showIcon, type }) => (
  <div className="Alert" data-testid="Alert">
    Alert Component
  </div>
);

export default Alert;
