import React, { FC, useState } from 'react';
import '../Form.css';
import './ContactForm.css';
import { Util } from '../../../utils/Util';
import i18n from '../../../utils/languages/I18N';

interface ContactFormProps { }

interface IResult {
  isLoading: boolean,
  isError: boolean,
  errorMessage: string,
  successMessage: string
  data: Object | null,
}

let iContact = {
  email: "",
  name: "",
  subject: "",
  message: "",
  id: "contact-form",
  disable: true
}

const ContactForm: FC<ContactFormProps> = () => {
  const [contact, setContact] = useState(iContact);
  const [result, setResult] = useState<IResult>();

  const handleChange = (event: any) => {
    iContact = { ...contact, [event.target.name]: event.target.value };
    iContact.disable = !(Util.checkMail(iContact.email) && Util.checkName(iContact.name) && Util.checkName(iContact.email) && iContact.subject === iContact.message);
    setContact(iContact);
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (iContact.disable) return false;
  }
  return (
    <div className="ContactForm" data-testid="ContactForm">
      <div className='row'>
        <h4 className='form-title'> {i18n.t("contact.joining")}</h4>
      </div>
      <div className='row'>
        <form method='post' action='#' id={iContact.id} onSubmit={(event) => handleSubmit(event)}>
          <div>
            <div className='row'>
              <label className='col-100' htmlFor="email"> {i18n.t("contact.email")} :  </label>
              <input
                className='col-100'
                type="email"
                id="email"
                name="email"
                placeholder={i18n.t("contact.typeEmail")}
                onChange={(event) => handleChange(event)}
                value={contact.email} />
            </div>
            <div className='row'>
              <label className='col-100' htmlFor="name"> {i18n.t("contact.name")}  </label>
              <input
                className='col-100'
                type="text"
                id="name"
                name="yourName"
                placeholder={i18n.t("contact.yourName")}
                onChange={(event) => handleChange(event)}
                value={contact.name} />
            </div>
            <div className='row'>
              <label className='col-100' htmlFor="message"> {i18n.t("contact.subject")}</label>
              <div className='row'>
                {/* <label htmlFor="select-form">  {i18n.t("contact.selectForm")}</label> */}
                <div className='row'>
                  <select name="sujet" id="sujet" required>
                    {/* <option
                      value="input" disabled selected hidden>Choisissez le sujet de votre message</option> */}
                    <option
                      value="probleme-compte">{i18n.t("contact.inputSelect1")}  </option>
                    <option
                      value="question-connexion"> {i18n.t("contact.inputSelect2")} </option>
                    <option
                      value="echec-connexion">{i18n.t("contact.inputSelect3")}  </option>

                    <option
                      value="autre">{i18n.t("contact.inputSelect4")} </option>
                    <option
                      value="suggestion">{i18n.t("contact.inputSelect5")} </option>
                  </select>
                </div>

              </div>
              <div>
                <label htmlFor="message-form"> {i18n.t("contact.message")}</label>
                <textarea
                  id="message"
                  name="message"
                  placeholder={i18n.t("contact.messageFields")}
                  area-required="true"></textarea>
              </div>

            </div>
            <div className='col-100' >
              <input type="submit" value={i18n.t("contact.send")} />
            </div>
          </div>
        </form>
      </div>
      <div>
      </div>
    </div>);
}

export default ContactForm;

