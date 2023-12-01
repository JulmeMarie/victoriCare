import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import EventView from './EventView';

describe('<EventView />', () => {
  test('it should mount', () => {
    render(<EventView />);
    
    const eventView = screen.getByTestId('EventView');

    expect(eventView).toBeInTheDocument();
  });
});