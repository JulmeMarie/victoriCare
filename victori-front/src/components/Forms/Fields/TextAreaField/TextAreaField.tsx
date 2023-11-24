import React, { FC } from 'react';
import './TextAreaField.css';

interface TextAreaFieldProps {
  name?: string
  onChange: (type: string, value: string) => void,
  defaultValue: string,
  required?: boolean,
  rows?: number
  placeHolder?: string
}

const TextAreaField: FC<TextAreaFieldProps> = ({ name, onChange, required, defaultValue, rows, placeHolder }) => {
  const defaultRows = 5;

  return (
    <div className="TextAreaField input-row" data-testid="TextAreaField">
      <textarea
        className='round-corner-input'
        name={name || "textarea"}
        rows={rows || defaultRows}
        placeholder={placeHolder}
        value={defaultValue}
        onChange={(event) => onChange(name || "textarea", event.target.value)}
        area-required={required}></textarea>
    </div>
  );
}

export default TextAreaField;
