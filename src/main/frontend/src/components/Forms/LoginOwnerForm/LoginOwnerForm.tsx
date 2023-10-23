import React, { FC, useEffect, useState } from 'react';
import i18n from '../../../utils/languages/I18N';
import { Util } from '../../../utils/Util';
//import { LoginService } from '../../../services/LoginService';
import './LoginOwnerForm.css';
import { Alert } from 'antd';

interface IResult {
  isLoading: boolean,
  isError: boolean,
  errorMessage: string,
  successMessage: string
  data: Object | null,
}

interface LoginOwnerFormProps {
  setFormId: (id: string) => void,
}

export let iForm = {
  email: "",
  password: "",
  id: "login-owner-form",
  disable: true
}

const LoginOwnerForm: FC<LoginOwnerFormProps> = ({ setFormId }) => {
  const [form, setForm] = useState(iForm);
  const [result, setResult] = useState<IResult>();
  //const service = LoginService.newInstance();

  useEffect(() => {

  });

  const handleChange = (event: any) => {
    iForm = { ...form, [event.target.name]: event.target.value };
    iForm.disable = !(Util.checkMail(iForm.email) && Util.checkPassword(iForm.password));
    setForm(iForm);
  }


  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (iForm.disable) return false;

    //postData("login", { email: iForm.email, password: iForm.password });
  }

  return (
    <div className='LoginOwnerForm' data-tested-id='LoginOwnerForm'>
      <form method='post' action='#' id={iForm.id} onSubmit={(event) => handleSubmit(event)}>
        <div className='row'>
          {result && result.isError && <Alert message="une erreur s'est produite" showIcon type='error' />}
        </div>
        <div className='row input-row'>
          <label className={" col-100 " + (iForm.email && iForm.email.length > 0 ? "" : "active")} htmlFor="email"> {i18n.t("login.email")}  </label>
          <input
            className='col-100'
            type="email"
            id="email"
            name="email"
            placeholder={i18n.t("login.typeEmail")}
            onChange={(event) => handleChange(event)}
            value={form.email} />
        </div>
        <div className='row input-row'>
          <label className={" col-100 " + (iForm.password && iForm.password.length > 0 ? "" : "hide")} htmlFor='password'> {i18n.t("login.password")}  </label>
          <input
            className='col-100'
            type="password"
            id="password"
            name="password"
            placeholder={i18n.t("login.typePassword")}
            value={form.password}
            onChange={(event) => handleChange(event)} />
        </div>
        <div className='row'>
          <div
            className='form-password-forgot'
            onClick={() => { setFormId("login-recovery-form") }}>{i18n.t("login.passwordForgotten")} ?
          </div>
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.connect")} disabled={form.disable} />
        </div>
      </form ></div >);
};

export default LoginOwnerForm;
