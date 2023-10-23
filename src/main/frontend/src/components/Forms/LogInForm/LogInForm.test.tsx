import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import LogInForm from './LogInForm';

describe('<LogInForm />', () => {
  test('it should mount', () => {
    render(<LogInForm />);
    
    const logInForm = screen.getByTestId('LogInForm');

    expect(logInForm).toBeInTheDocument();
  });
});