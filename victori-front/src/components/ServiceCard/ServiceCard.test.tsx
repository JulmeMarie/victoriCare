import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ServiceCard from './ServiceCard';
import { IoPerson } from 'react-icons/io5';

const serviceTest = {
  title: "test",
  paragraphs: [],
  icon: 'IoPerson'
}

describe('<ServiceCard />', () => {
  test('it should mount', () => {
    render(<ServiceCard service={serviceTest} />);

    const serviceCard = screen.getByTestId('ServiceCard');

    expect(serviceCard).toBeInTheDocument();
  });
});