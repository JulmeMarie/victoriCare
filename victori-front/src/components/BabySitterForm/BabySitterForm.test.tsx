import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabySitterForm from './BabySitterForm';

describe('<BabySitterForm />', () => {
  test('it should mount', () => {
    render(<BabySitterForm />);
    
    const babySitterForm = screen.getByTestId('BabySitterForm');

    expect(babySitterForm).toBeInTheDocument();
  });
});