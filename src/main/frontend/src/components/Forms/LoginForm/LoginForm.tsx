import React, { FC, useEffect, useState } from 'react';
import { FaLock } from 'react-icons/fa';
import i18n from '../../../utils/languages/I18N';
import { Util } from '../../../utils/Util';
import Alert from '../../Alert/Alert';
//import { LoginService } from '../../../services/LoginService';
import './LoginForm.css';

interface IResult {
  isLoading: boolean,
  isError: boolean,
  errorMessage: string,
  successMessage: string
  data: Object | null,
}

export let iForm = {
  email: "",
  password: "",
  id: "login-owner-form",
  yourName: null,
  accountName: null,
  code: null,
  disable: true
}

interface LoginFormProps {
  setFormId: (id: string) => void,
}

const LoginForm: FC<LoginFormProps> = ({ setFormId }) => {

  const [form, setForm] = useState(iForm);
  const [result, setResult] = useState<IResult>();
  //const service = LoginService.newInstance();

  useEffect(() => {

  });

  const handleChange = (key: string, value: any) => {
    iForm = { ...form, [key]: value };
    iForm.disable = !(Util.checkMail(iForm.email) && Util.checkPassword(iForm.password));
    setForm(iForm);
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (iForm.disable) return false;

    //postData("login", { email: iForm.email, password: iForm.password });
  }


  return (
    <section className="LoginForm" data-testid="LoginForm">
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
        <div className='row' id='login-access-form'>
          <div className='row'>
            <input
              className='col-100'
              type="text" id="yourName"
              name="yourName"
              placeholder={i18n.t("login.typeLastname")}
              onChange={(event) => handleChange("yourName", event.target.value)}
            />
          </div>
          <div className='row'>
            <input
              className='col-100'
              type="text" id="accountName"
              name="accountName"
              placeholder={i18n.t("login.typeAssociatedLastname")}
              onChange={(event) => handleChange("accountName", event.target.value)}
            />
          </div>
          <div className='row'>
            <input
              className='col-100'
              type="number" id="code"
              name="code"
              placeholder={i18n.t("login.typeAccessCode")}
              onChange={(event) => handleChange("code", event.target.value)}
            />
          </div>
        </div>
        <div className='row' id='login-owner-form'>
          <div className='row input-row'>
            <input
              className='col-100'
              type="email"
              id="email"
              name="email"
              placeholder={i18n.t("login.typeEmail")}
              onChange={(event) => handleChange("email", event.target.value)}
              value={form.email} />
          </div>
          <div className='row input-row'>
            <input
              className='col-100'
              type="password"
              id="password"
              name="password"
              placeholder={i18n.t("login.typePassword")}
              value={form.password}
              onChange={(event) => handleChange("password", event.target.value)} />
          </div>
          <div className='row'>
            <div
              className='form-link'
              onClick={() => { setFormId("login-recovery-form") }}>{i18n.t("login.passwordForgotten")} ?
            </div>
          </div>
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.connect")} disabled={form.disable} />
        </div>
      </form>
    </section>
  );
}

export default LoginForm;
