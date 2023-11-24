import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Badge from './Badge';

describe('<Badge />', () => {
  test('it should mount', () => {
    render(<Badge />);

    const sideNav = screen.getByTestId('Badge');

    expect(sideNav).toBeInTheDocument();
  });
});