import React, { FC, useState } from 'react';
import '../Form.css';
import './ContactForm.css';
import i18n from '../../../utils/languages/I18N';
import { IResult, ISelectOption } from '../../../utils/global-interfaces';
import SubmitButton from '../SubmitButton/SubmitButton';
import { ALERTS, FORMNAMES } from '../../../utils/Constants';
import { defaultResult } from '../../../utils/global-default-values';
import { ContactService } from '../../../services/ContactService';
import Alert from '../../Alert/Alert';

interface ContactFormProps { }

export interface IContactForm {
  email: string,
  username: string,
  subject: "",
  message: string,
  disable: boolean,
  name: string
}

const options = [
  'contact.accountProblem',
  'contact.accountQuestion',
  'contact.connectionFailed',
  'contact.suggestion',
  'contact.others'
]

const defaultValues = {
  email: "",
  username: "",
  subject: i18n.t(options[0]),
  message: "",
  disable: true,
  name: FORMNAMES.CONTACT
} as IContactForm;

const service = new ContactService();
const ContactForm: FC<ContactFormProps> = () => {
  const [formValues, setFormValues] = useState<IContactForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const handleChange = (key: string, value: any) => {
    setFormValues(values => {
      const newVvalues = { ...values, [key]: value };
      service.validate(newVvalues);
      return newVvalues;
    });
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    setResult(result => ({ ...result, isLoading: true }));
    //return false;
    service.contact(formValues).then(response => {
      setResult(response);
    });
  }

  return (
    <div className="ContactForm" data-testid="ContactForm">
      <div className='row'>
        <h4 className='form-title'> {i18n.t("contact.joining")}</h4>
      </div>
      <div className='row'>
        <form method='post' action='#' onSubmit={(event) => handleSubmit(event)}>
          <div>
            <div className='row'>
              {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
              {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
            </div>
            <div className='row'>
              <label className='col-100' htmlFor="email"> {i18n.t("contact.email")} :  </label>
              <input
                className='col-100'
                type="email"
                id="email"
                name="email"
                placeholder={i18n.t("contact.typeEmail")}
                onChange={(event) => handleChange("email", event.target.value)}
                value={formValues.email}
                autoComplete="on" />
            </div>
            <div className='row'>
              <label className='col-100' htmlFor="username"> {i18n.t("contact.name")}  </label>
              <input
                className='col-100'
                type="text"
                id="username"
                name="username"
                placeholder={i18n.t("contact.username")}
                onChange={(event) => handleChange("username", event.target.value)}
                value={formValues.username}
                autoComplete="on" />
            </div>
            <div className='row'>
              <label className='col-100' htmlFor="subject"> {i18n.t("contact.subject")}</label>
              <select
                id="subject"
                name="subject"
                value={formValues.subject}
                onChange={(event) => handleChange("subject", i18n.t(event.target.value))}
                required>
                {options.map(option => (
                  <option key={option} value={option}>
                    {i18n.t(option)}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="message"> {i18n.t("contact.message")}</label>
              <textarea
                id="message"
                name="message"
                rows={6}
                placeholder={i18n.t("contact.messageFields")}
                value={formValues.message}
                onChange={(event) => handleChange("message", event.target.value)}
                area-required="true"></textarea>
            </div>
            <div className='row form-footer'>
              <SubmitButton
                label={i18n.t("contact.send")}
                isLoading={result.isLoading}
                isDisabled={formValues.disable} />
            </div>
          </div>
        </form>
      </div>
    </div>);
}
export default ContactForm;