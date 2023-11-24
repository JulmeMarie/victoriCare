import React, { FC } from 'react';
import './PasswordInput.css';
import { IoLockClosed } from 'react-icons/io5';
import { LABELS } from '../../../../utils/Constants';

interface PasswordInputProps {
  name?: string
  onChange: (type: string, value: string) => void,
  defaultValue: string
}

const PasswordInput: FC<PasswordInputProps> = ({ name, onChange, defaultValue }) => {

  return (
    <div className="PasswordInput input-row" data-testid="PasswordInput">
      <input
        className='round-corner-input col-100'
        type="password"
        name={name || "password"}
        placeholder={LABELS.typeYourPassword}
        onChange={(event) => onChange(name || "password", event.target.value)}
        value={defaultValue}
        autoComplete="off" />
      <IoLockClosed />
    </div>
  );
}

export default PasswordInput;
