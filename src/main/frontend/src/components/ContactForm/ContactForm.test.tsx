import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import ContactForm from './ContactForm';

describe('<ContactForm />', () => {
  test('it should mount', () => {
    render(<ContactForm />);
    
    const contactForm = screen.getByTestId('ContactForm');

    expect(contactForm).toBeInTheDocument();
  });
});