import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabyCard from './BabyCard';

describe('<BabyCard />', () => {
  test('it should mount', () => {
    render(<BabyCard />);
    
    const babyCard = screen.getByTestId('BabyCard');

    expect(babyCard).toBeInTheDocument();
  });
});