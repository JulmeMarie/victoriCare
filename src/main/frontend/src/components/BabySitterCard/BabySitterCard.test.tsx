import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabySitterCard from './BabySitterCard';

describe('<BabySitterCard />', () => {
  test('it should mount', () => {
    render(<BabySitterCard />);
    
    const babySitterCard = screen.getByTestId('BabySitterCard');

    expect(babySitterCard).toBeInTheDocument();
  });
});