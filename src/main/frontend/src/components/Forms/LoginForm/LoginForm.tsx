import React, { FC, useEffect, useState } from 'react';
import { FaEnvelope, FaLock, FaUser, FaKey } from 'react-icons/fa';
import { FormType } from '../../../utils/Enums';
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

interface ILoginForm {
  email: string,
  password: string,
  type: string,
  yourName: string,
  accountName: string,
  code: Number,
  disable: boolean
}

export let defaultFormValues: ILoginForm = {
  email: "",
  password: "",
  type: FormType.OWNER,
  yourName: "",
  accountName: "",
  code: 0,
  disable: true
}

interface LoginFormProps {
  setFormId: (id: string) => void,
}

const LoginForm: FC<LoginFormProps> = ({ setFormId }) => {
  const [formValues, setFormValues] = useState<ILoginForm>(defaultFormValues);
  const [result, setResult] = useState<IResult>();
  //const service = LoginService.newInstance();

  const handleChange = (key: string, value: any) => {
    const values = { ...formValues, [key]: value };
    values.disable = !(Util.checkMail(values.email) && Util.checkPassword(values.password));
    setFormValues(values);
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    if (formValues.disable) return false;

    //postData("login", { email: iForm.email, password: iForm.password });
  }

  const isParent = () => {
    return formValues.type === FormType.OWNER;
  }

  const isBabySitter = () => {
    return formValues.type === FormType.ACCESS;
  }


  return (
    <section className="LoginForm" data-testid="LoginForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('login.authentication')}</h1>
      </div>
      <div className='row form-buttons'>
        <button
          className={isParent() ? 'head-form-button active' : 'head-form-button'}
          onClick={() => { setFormValues({ ...formValues, type: FormType.OWNER }) }}>{i18n.t("login.parent")}
        </button>
        <button
          className={isBabySitter() ? 'head-form-button active' : 'head-form-button'}
          onClick={() => { setFormValues({ ...formValues, type: FormType.ACCESS }) }}>{i18n.t("login.babysitter")}
        </button>
      </div>
      <form method='post' action='#' onSubmit={handleSubmit}>
        {isBabySitter() &&
          <div className='row' id={FormType.ACCESS}>
            <div className='row input-row'>
              <input
                className='col-100'
                type="text" id="yourName"
                name="yourName"
                placeholder={i18n.t("login.typeLastname")}
                onChange={(event) => handleChange("yourName", event.target.value)}
              />
              <FaUser />
            </div>
            <div className='row input-row'>
              <input
                className='col-100'
                type="text" id="accountName"
                name="accountName"
                placeholder={i18n.t("login.typeAssociatedLastname")}
                onChange={(event) => handleChange("accountName", event.target.value)}
              />
              <FaUser />
            </div>
            <div className='row input-row'>
              <input
                className='col-100'
                type="number" id="code"
                name="code"
                placeholder={i18n.t("login.typeAccessCode")}
                onChange={(event) => handleChange("code", event.target.value)}
              />
              <FaKey />
            </div>
          </div>
        }
        {isParent() &&
          <div className='row' id={FormType.OWNER}>
            <div className='row input-row'>
              <input
                className='col-100'
                type="email"
                id="email"
                name="email"
                placeholder={i18n.t("login.typeEmail")}
                onChange={(event) => handleChange("email", event.target.value)}
                value={formValues.email} />
              <FaEnvelope />
            </div>
            <div className='row input-row'>
              <input
                className='col-100'
                type="password"
                id="password"
                name="password"
                placeholder={i18n.t("login.typePassword")}
                value={formValues.password}
                onChange={(event) => handleChange("password", event.target.value)} />
              <FaLock />
            </div>
            <div className='row'>
              <div
                className='form-link'
                onClick={() => { setFormId("login-recovery-form") }}>{i18n.t("login.passwordForgotten")} ?
              </div>
            </div>
          </div>
        }
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.connect")} disabled={formValues.disable} />
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => { setFormId(FormType.SIGNIN) }}>{i18n.t("login.noaccount")} ?
          </div>
        </div>
      </form>
    </section>
  );
}

export default LoginForm;
