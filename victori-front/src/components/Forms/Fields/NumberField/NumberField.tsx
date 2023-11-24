import React, { FC } from 'react';
import './NumberField.css';

interface NumberFieldProps { }

const NumberField: FC<NumberFieldProps> = () => (
  <div className="NumberField" data-testid="NumberField">
    NumberField Component
  </div>
);

export default NumberField;
