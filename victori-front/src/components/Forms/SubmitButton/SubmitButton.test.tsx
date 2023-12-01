import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import SubmitButton from './SubmitButton';

describe('<SubmitButton />', () => {
  test('it should mount', () => {
    render(<SubmitButton label='test' isLoading={false} isDisabled={false} />);

    const submitButton = screen.getByTestId('SubmitButton');

    expect(submitButton).toBeInTheDocument();
  });
});