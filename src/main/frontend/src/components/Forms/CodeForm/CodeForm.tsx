import React, { FC } from 'react';
import './CodeForm.css';

interface CodeFormProps {}

const CodeForm: FC<CodeFormProps> = () => (
  <div className="CodeForm" data-testid="CodeForm">
    CodeForm Component
  </div>
);

export default CodeForm;
