import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabyDetails from './BabyDetails';

describe('<BabyDetails />', () => {
  test('it should mount', () => {
    render(<BabyDetails />);
    
    const babyDetails = screen.getByTestId('BabyDetails');

    expect(babyDetails).toBeInTheDocument();
  });
});