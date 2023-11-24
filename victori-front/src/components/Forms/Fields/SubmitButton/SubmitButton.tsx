import React, { FC } from 'react';
import { FaSpinner } from 'react-icons/fa';
import './SubmitButton.css';

interface SubmitButtonProps {
  label: string;
  isDisabled: boolean;
  isLoading: boolean;
}

const SubmitButton: FC<SubmitButtonProps> = ({ label, isDisabled, isLoading }) => (

  <div className='SubmitButton input-row' data-testid="SubmitButton">
    <button
      type='submit'
      className={`round-corner-input ${isLoading ? 'loading-button' : 'submit-button'}`}
      disabled={isDisabled || isLoading}
    >
      {isLoading ? <FaSpinner /> : label}
    </button>
  </div>
);

export default SubmitButton;
