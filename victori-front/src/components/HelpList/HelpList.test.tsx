import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import HelpList from './HelpList';

describe('<HelpList />', () => {
  test('it should mount', () => {
    render(<HelpList />);

    const list = screen.getByTestId('HelpList');

    expect(list).toBeInTheDocument();
  });
});