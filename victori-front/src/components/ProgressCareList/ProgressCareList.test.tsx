import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ProgressCareList from './ProgressCareList';

describe('<ProgressCareList />', () => {
  test('it should mount', () => {
    render(<ProgressCareList />);
    
    const progressCareList = screen.getByTestId('ProgressCareList');

    expect(progressCareList).toBeInTheDocument();
  });
});