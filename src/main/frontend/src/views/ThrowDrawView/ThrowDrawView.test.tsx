import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ThrowDrawView from './ThrowDrawView';

describe('<ThrowDrawView />', () => {
  test('it should mount', () => {
    render(<ThrowDrawView />);
    
    const throwDrawView = screen.getByTestId('ThrowDrawView');

    expect(throwDrawView).toBeInTheDocument();
  });
});