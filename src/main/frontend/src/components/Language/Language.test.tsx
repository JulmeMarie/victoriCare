import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Language from './Language';

describe('<Language />', () => {
  test('it should mount', () => {
    render(<Language />);
    
    const language = screen.getByTestId('Language');

    expect(language).toBeInTheDocument();
  });
});