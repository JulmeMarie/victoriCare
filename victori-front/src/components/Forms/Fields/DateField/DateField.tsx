import React, { FC } from 'react';
import './DateField.css';

interface DateFieldProps { }

const DateField: FC<DateFieldProps> = () => (
  <div className="DateField" data-testid="DateField">
    DateField Component
  </div>
);

export default DateField;
