import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ServiceCard from './ServiceCard';
import { FaUser } from 'react-icons/fa';

const serviceTest = {
  title: "test",
  paragraphs: [],
  icon: <FaUser />
}

describe('<ServiceCard />', () => {
  test('it should mount', () => {
    render(<ServiceCard service={serviceTest} />);

    const serviceCard = screen.getByTestId('ServiceCard');

    expect(serviceCard).toBeInTheDocument();
  });
});