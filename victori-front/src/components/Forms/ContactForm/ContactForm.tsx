import React, { FC } from 'react';
import './ContactForm.css';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';
import { LABELS } from '../../../utils/Constants';
import { FaEnvelope } from 'react-icons/fa';
import FormAlert from '../FormAlert/FormAlert';
import TextField from '../Fields/TextField/TextField';
import EmailField from '../Fields/EmailField/EmailField';
import SelectField from '../Fields/SelectField/SelectField';
import TextAreaField from '../Fields/TextAreaField/TextAreaField';
import useContactForm from '../../../customHooks/useContactForm';

interface ContactFormProps { }

const ContactForm: FC<ContactFormProps> = () => {

  const { options, values, result, handleChange, handleSubmit } = useContactForm();

  return (
    <div className="ContactForm Form" data-testid="ContactForm">

      <h4 className='form-title'><FaEnvelope /> {LABELS.contactUs}</h4>

      <form method='post' action='#' onSubmit={(event) => handleSubmit(event)}>

        <FormAlert result={result} />

        <TextField
          name='username'
          onChange={handleChange}
          defaultValue={values.username} />

        <EmailField
          onChange={handleChange}
          defaultValue={values.email} />

        <SelectField
          name="subject"
          options={options}
          onChange={handleChange}
          defaultValue={values.subject} />

        <TextAreaField
          name='message'
          onChange={handleChange}
          defaultValue={values.message}
          placeHolder={LABELS.messagePlaceHolder} />

        <SubmitButton
          label={LABELS.toSend}
          isLoading={result.isLoading}
          isDisabled={values.disable} />
      </form>
    </div>);
}
export default ContactForm;