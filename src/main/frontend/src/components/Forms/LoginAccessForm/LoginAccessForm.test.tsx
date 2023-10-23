import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import LoginAccessForm from './LoginAccessForm';

describe('<LoginAccessForm />', () => {
  test('it should mount', () => {
    render(<LoginAccessForm />);
    
    const loginAccessForm = screen.getByTestId('LoginAccessForm');

    expect(loginAccessForm).toBeInTheDocument();
  });
});