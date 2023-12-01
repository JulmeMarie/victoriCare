import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabyForm from './BabyForm';

describe('<BabyForm />', () => {
  test('it should mount', () => {
    render(<BabyForm />);
    
    const babyForm = screen.getByTestId('BabyForm');

    expect(babyForm).toBeInTheDocument();
  });
});