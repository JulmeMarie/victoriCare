import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ProgressCare from './ProgressCare';

describe('<ProgressCare />', () => {
  test('it should mount', () => {
    render(<ProgressCare />);

    const progressCare = screen.getByTestId('ProgressCare');

    expect(progressCare).toBeInTheDocument();
  });
});