import React, { FC } from 'react';
import './Alert.css';
import { EAlertType } from '../../utils/global-types';
import { FaCheckCircle, FaExclamationCircle, FaInfoCircle, FaTimesCircle } from 'react-icons/fa';

interface AlertProps {
  message?: string,
  showIcon: boolean,
  type: EAlertType
}

const icons = [
  { key: 'success', value: <FaCheckCircle /> },
  { key: 'warning', value: <FaExclamationCircle /> },
  { key: 'info', value: <FaInfoCircle /> },
  { key: 'error', value: <FaTimesCircle /> }
]

const Alert: FC<AlertProps> = ({ message, showIcon, type }) => {
  const getIcon = (iconString: string) => {
    return icons.find(icon => icon.key === iconString);
  }
  return (
    <div className={`flex-row Alert ${type}`} data-testid="Alert">
      <div className='Alert-icon'>
        {getIcon(type)?.value}
      </div>
      <div className='Alert-message'>
        {message}
      </div>
    </div>
  );
}

export default Alert;
