import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabySitterDetails from './BabySitterDetails';

describe('<BabySitterDetails />', () => {
  test('it should mount', () => {
    render(<BabySitterDetails />);
    
    const babySitterDetails = screen.getByTestId('BabySitterDetails');

    expect(babySitterDetails).toBeInTheDocument();
  });
});