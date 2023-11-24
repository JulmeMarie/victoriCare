import React, { FC } from 'react';
import './TextField.css';
import { IoPerson } from 'react-icons/io5';
import { LABELS } from '../../../../utils/Constants';

interface TextFieldProps {
  name?: string
  onChange: (type: string, value: string) => void,
  defaultValue: string
}

const TextField: FC<TextFieldProps> = ({ name, onChange, defaultValue }) => {
  return (
    <div className="TextField input-row" data-testid="TextField">
      <input
        className='round-corner-input col-100'
        type="text"
        name={name || "text"}
        placeholder={LABELS.typeYourName}
        onChange={(event) => onChange(name || "text", event.target.value)}
        value={defaultValue}
        autoComplete="on" />
      <IoPerson />
    </div>
  );
}

export default TextField;
