import React, { FC } from 'react';
import './ContactForm.css';

interface ContactFormProps {}

const ContactForm: FC<ContactFormProps> = () => (
  <div className="ContactForm" data-testid="ContactForm">
    ContactForm Component
  </div>
);

export default ContactForm;
