import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Drawer from './Drawer';

describe('<Drawer />', () => {
  test('it should mount', () => {
    render(<Drawer />);

    const sideNav = screen.getByTestId('Drawer');

    expect(sideNav).toBeInTheDocument();
  });
});