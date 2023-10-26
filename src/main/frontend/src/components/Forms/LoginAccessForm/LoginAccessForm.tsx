import React, { FC, useState } from 'react';
import { FaLock } from 'react-icons/fa';
import i18n from '../../../utils/languages/I18N';
import './LoginAccessForm.css';

interface LoginAccessFormProps {
  setFormId: (id: string) => void,
}

let iForm = {
  yourName: null,
  accountName: null,
  code: null,
  id: "login-access-form",
  disable: true
}

const LoginAccessForm: FC<LoginAccessFormProps> = ({ setFormId }) => {
  const [form, setForm] = useState(iForm);

  const handleChange = (event: any) => {
    setForm({ ...form, [event.target.name]: event.target.value });
  }
  const [disable, setDisable] = useState<boolean>(true);

  const handleSubmit = () => {

  }

  return (
    <section className="LoginAccessForm" data-tested-id="LoginAccessForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('login.authentication')}</h1>
      </div>
      <div className='row'>
        <div
          className='form-link head-form-link'
          onClick={() => { setFormId("login-owner-form") }}>{i18n.t("login.parent")}
        </div>
      </div>
      <form method='post' action='#' id={iForm.id} onSubmit={handleSubmit}>
        <div className='row'>
          <label className='col-100' htmlFor="yourName"> {i18n.t("login.yourLastname")} :  </label>
          <input
            className='col-100'
            type="text" id="yourName"
            name="yourName"
            placeholder={i18n.t("login.typeLastname")} />
        </div>
        <div className='row'>
          <label className='col-100' htmlFor="accountName"> {i18n.t("login.associatedLastname")} :  </label>
          <input
            className='col-100'
            type="text" id="accountName"
            name="accountName"
            placeholder={i18n.t("login.typeAssociatedLastname")} />
        </div>
        <div className='row'>
          <label className='col-100' htmlFor='code'> {i18n.t("login.accessCode")} : </label>
          <input
            className='col-100'
            type="number" id="code"
            name="code"
            placeholder={i18n.t("login.typeAccessCode")} />
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.connect")} disabled={disable} />
        </div>
      </form>
    </section>
  )
}

export default LoginAccessForm;
