import React, { FC } from 'react';
import './EmailField.css';
import { IoMail } from 'react-icons/io5';
import { LABELS } from '../../../../utils/Constants';

interface EmailFieldProps {
  name?: string
  onChange: (type: string, value: string) => void,
  defaultValue: string
}

const EmailField: FC<EmailFieldProps> = ({ name, onChange, defaultValue }) => {
  return (
    <div className="EmailField input-row" data-testid="EmailField">
      <input
        className='round-corner-input col-100'
        type="email"
        name={name || "email"}
        placeholder={LABELS.typeYourMail}
        onChange={(event) => onChange(name || "email", event.target.value)}
        value={defaultValue}
        autoComplete="on" />
      <IoMail />
    </div>
  );
}

export default EmailField;
