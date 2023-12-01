import React, { FC } from 'react';
import { FaSpinner } from 'react-icons/fa';
import './SubmitButton.css';

interface SubmitButtonProps {
  label: string;
  isDisabled: boolean;
  isLoading: boolean;
}

const SubmitButton: FC<SubmitButtonProps> = ({ label, isDisabled, isLoading }) => (
  <button
    data-testid="SubmitButton"
    type='submit'
    className={isLoading ? 'loading-button' : 'submit-button'}
    disabled={isDisabled || isLoading}
  >
    {isLoading ? <FaSpinner /> : label}
  </button>
);

export default SubmitButton;
