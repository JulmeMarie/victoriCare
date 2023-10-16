import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import BabyList from './BabyList';

describe('<BabyList />', () => {
  test('it should mount', () => {
    render(<BabyList />);
    
    const babyList = screen.getByTestId('BabyList');

    expect(babyList).toBeInTheDocument();
  });
});