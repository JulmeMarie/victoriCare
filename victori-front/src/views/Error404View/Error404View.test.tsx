import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Error404View from './Error404View';

describe('<Error404View />', () => {
  test('it should mount', () => {
    render(<Error404View />);
    
    const error404View = screen.getByTestId('Error404View');

    expect(error404View).toBeInTheDocument();
  });
});