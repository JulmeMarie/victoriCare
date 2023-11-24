import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import CodeForm from './CodeForm';

describe('<CodeForm />', () => {
  test('it should mount', () => {
    render(<CodeForm />);

    const codeForm = screen.getByTestId('CodeForm');

    expect(codeForm).toBeInTheDocument();
  });
});