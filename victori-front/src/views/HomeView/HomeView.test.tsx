import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import HomeView from './HomeView';

describe('<HomeView />', () => {
  test('it should mount', () => {
    render(<HomeView />);
    
    const homeView = screen.getByTestId('HomeView');

    expect(homeView).toBeInTheDocument();
  });
});