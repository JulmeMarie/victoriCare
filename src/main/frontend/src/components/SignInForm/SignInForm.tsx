import React, { FC, useState } from 'react';
import { Util } from '../../utils/Util';
import i18n from '../../utils/languages/I18N';
import './SignInForm.css';

interface SignInFormProps { }

let signInForm = {
  email: "",
  password1: "",
  password2: "",
  id: "signin-form",
  disable: true
}

const SignInForm: FC<SignInFormProps> = () => {
  const [form, setForm] = useState(signInForm);

  const handleChange = (event: any) => {
    signInForm = { ...form, [event.target.name]: event.target.value };
    signInForm.disable = !(Util.checkMail(signInForm.email) && Util.checkPassword(signInForm.password));
    setForm(signInForm);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (signInForm.disable) return false;
    postData("login", { email: signInForm.email, password: signInForm.password });
  }

  return (<form method='post' action='#' id={signInForm.id} onSubmit={(event) => handleSubmit(event)}>
    <div className='row'>
      {status.isError && <Alert message="une erreur s'est produite" showIcon type='error' />}
    </div>
    <div className='row'>
      <label className='col-100' htmlFor="email"> {i18n.t("login.email")} :  </label>
      <input
        className='col-100'
        type="email"
        id="email"
        name="email"
        placeholder={i18n.t("login.typeEmail")}
        onChange={(event) => handleChange(event)}
        value={form.email} />
    </div>
    <div className='row'>
      <label className='col-100' htmlFor='password'> {i18n.t("login.password")} : </label>
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
        onClick={() => { setFormId(loginRecoveryForm.id) }}>{i18n.t("login.passwordForgotten")} ?
      </div>
    </div>
    <div className='row form-footer'>
      <input type="submit" value={i18n.t("login.connect")} disabled={form.disable} />
    </div>
  </form>)
}


export default SignInForm;
