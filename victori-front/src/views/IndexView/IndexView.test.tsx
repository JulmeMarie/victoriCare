import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import IndexView from './IndexView';

describe('<IndexView />', () => {
  test('it should mount', () => {
    render(<IndexView logIn={null} />);

    const indexView = screen.getByTestId('IndexView');

    expect(indexView).toBeInTheDocument();
  });
});