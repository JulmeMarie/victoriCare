import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import PasswordForm from './PasswordForm';

describe('<PasswordForm />', () => {
  test('it should mount', () => {
    render(<PasswordForm />);

    const passwordForm = screen.getByTestId('PasswordForm');

    expect(passwordForm).toBeInTheDocument();
  });
});