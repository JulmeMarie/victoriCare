import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import FirstnameView from './FirstnameView';

describe('<FirstnameView />', () => {
  test('it should mount', () => {
    render(<FirstnameView />);
    
    const firstnameView = screen.getByTestId('FirstnameView');

    expect(firstnameView).toBeInTheDocument();
  });
});