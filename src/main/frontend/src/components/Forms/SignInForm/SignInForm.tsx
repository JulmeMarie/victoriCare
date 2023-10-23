import React, { FC, useState } from 'react';
import { Util } from '../../../utils/Util';
import i18n from '../../../utils/languages/I18N';
import '../Form.css';
import './SignInForm.css';
import { Alert } from 'antd';
import { LockFilled } from '@ant-design/icons';

interface SignInFormProps { }

interface IResult {
  isLoading: boolean,
  isError: boolean,
  errorMessage: string,
  successMessage: string
  data: Object | null,
}

let iForm = {
  email: "",
  password1: "",
  password2: "",
  id: "signin-form",
  disable: true
}

const SignInForm: FC<SignInFormProps> = () => {
  const [form, setForm] = useState(iForm);
  const [result, setResult] = useState<IResult>();

  const handleChange = (event: any) => {
    iForm = { ...form, [event.target.name]: event.target.value };
    iForm.disable = !(Util.checkMail(iForm.email) && Util.checkPassword(iForm.password1) && Util.checkPassword(iForm.password1) && iForm.password1 === iForm.password2);
    setForm(iForm);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (iForm.disable) return false;
    //postData("login", { email: iForm.email, password: iForm.password });
  }

  return (
    <div className="SignInForm" data-testid="SignInForm">
      <div className='row'>
        <h3 className='form-title'><LockFilled /> {i18n.t('signin.register')}</h3>
      </div>
      <form method='post' action='#' id={iForm.id} onSubmit={(event) => handleSubmit(event)}>
        <div className='row'>
          {result && result.isError && <Alert message="une erreur s'est produite" showIcon type='error' />}
        </div>
        <div className='row'>
          <label className='col-100' htmlFor="email_s"> {i18n.t("signin.email")} :  </label>
          <input
            className='col-100'
            type="email"
            id="email_s"
            name="email"
            placeholder={i18n.t("signin.typeEmail")}
            onChange={(event) => handleChange(event)}
            value={form.email} />
        </div>
        <div className='row'>
          <label className='col-100' htmlFor='password1'> {i18n.t("signin.password1")} : </label>
          <input
            className='col-100'
            type="password"
            id="password1"
            name="password1"
            placeholder={i18n.t("signin.typePassword1")}
            value={form.password1}
            onChange={(event) => handleChange(event)} />
        </div>
        <div className='row'>
          <label className='col-100' htmlFor='password2'> {i18n.t("signin.password2")} : </label>
          <input
            className='col-100'
            type="password"
            id="password2"
            name="password2"
            placeholder={i18n.t("signin.checkPassword2")}
            value={form.password1}
            onChange={(event) => handleChange(event)} />
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("signin.validate")} disabled={form.disable} />
        </div>
      </form>
    </div>);
}


export default SignInForm;
