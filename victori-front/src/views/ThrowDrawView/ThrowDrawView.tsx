import React, { FC } from 'react';
import './ThrowDrawView.css';

interface ThrowDrawViewProps {}

const ThrowDrawView: FC<ThrowDrawViewProps> = () => (
  <div className="ThrowDrawView" data-testid="ThrowDrawView">
    ThrowDrawView Component
  </div>
);

export default ThrowDrawView;
