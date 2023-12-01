import React, { FC } from 'react';
import './FirstnameView.css';

interface FirstnameViewProps {}

const FirstnameView: FC<FirstnameViewProps> = () => (
  <div className="FirstnameView" data-testid="FirstnameView">
    FirstnameView Component
  </div>
);

export default FirstnameView;
