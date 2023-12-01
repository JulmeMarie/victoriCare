import React, { FC } from 'react';
import { AlertType } from '../../utils/global-interfaces';
import './Alert.css';

interface AlertProps {
  message?: string,
  showIcon: boolean,
  type: string
}

const Alert: FC<AlertProps> = ({ message, showIcon, type }) => (
  <div className={`row Alert ${type}`} data-testid="Alert">
    {message}
  </div>
);

export default Alert;
