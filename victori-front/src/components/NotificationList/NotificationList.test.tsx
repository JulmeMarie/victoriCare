import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import NotificationList from './NotificationList';

describe('<NotificationList />', () => {
  test('it should mount', () => {
    render(<NotificationList />);
    
    const notificationList = screen.getByTestId('NotificationList');

    expect(notificationList).toBeInTheDocument();
  });
});