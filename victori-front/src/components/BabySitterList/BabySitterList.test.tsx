import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabySitterList from './BabySitterList';

describe('<BabySitterList />', () => {
  test('it should mount', () => {
    render(<BabySitterList />);
    
    const babySitterList = screen.getByTestId('BabySitterList');

    expect(babySitterList).toBeInTheDocument();
  });
});