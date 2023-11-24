import React, { FC } from 'react';

interface SelectFieldProps {
  name?: string,
  options: Map<string, string>,
  onChange: (type: string, value: string) => void,
  defaultValue: string,
  required?: boolean
}

const SelectField: FC<SelectFieldProps> = ({ name, options, onChange, defaultValue, required }) => {
  return (
    <div className="SelectField input-row" data-testid="SelectField">
      <select
        className='round-corner-input'
        name={name || "select"}
        value={defaultValue}
        onChange={(event) => onChange(name || "select", event.target.value)}
        required={required}>
        {
          Array.from(options).map(
            ([key, value]) => (
              <option key={key} value={value}>
                {value}
              </option>
            ))
        }
      </select>
    </div>
  );
}
export default SelectField;
