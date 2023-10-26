import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import AboutView from './AboutView';

describe('<AboutView />', () => {
  test('it should mount', () => {
    render(<AboutView />);
    
    const aboutView = screen.getByTestId('AboutView');

    expect(aboutView).toBeInTheDocument();
  });
});