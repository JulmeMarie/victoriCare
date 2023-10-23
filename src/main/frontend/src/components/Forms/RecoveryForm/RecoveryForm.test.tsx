import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import RecoveryForm from './RecoveryForm';

describe('<RecoveryForm />', () => {
  test('it should mount', () => {
    render(<RecoveryForm />);
    
    const recoveryForm = screen.getByTestId('RecoveryForm');

    expect(recoveryForm).toBeInTheDocument();
  });
});