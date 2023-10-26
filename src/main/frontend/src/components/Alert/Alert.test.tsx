import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Alert from './Alert';



describe('<Alert />', () => {
  test('it should mount', () => {
    render(<Alert message={''} showIcon={false} type={'SUCCESS'} />);

    const alert = screen.getByTestId('Alert');

    expect(alert).toBeInTheDocument();
  });
});