import React, { FC, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { FaEnvelope, FaLock, FaUser, FaKey, FaSpinner } from 'react-icons/fa';
import { IResult } from '../../../utils/global-interfaces';
import HttpService from '../../../services/HttpService';
import { LogInService } from '../../../services/LoginService';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import i18n from '../../../utils/languages/I18N';
import { setLogIn, refreshToken } from '../../../redux/reducers/app-reducer';
import './LoginForm.css';
import { defaultResult } from '../../../utils/global-default-values';
import Alert from '../../Alert/Alert';
import SubmitButton from '../SubmitButton/SubmitButton';

interface LoginFormProps {
  setFormName: (name: string) => void,
}

export interface ILogInForm {
  email: string,
  password: string,
  name: string,
  yourName: string,
  accountName: string,
  code: Number,
  disable: boolean
}

export const defaultValues = {
  email: "",
  password: "",
  name: CONTENTSNAME.LOGIN_OWNER,
  yourName: "",
  accountName: "",
  code: 0,
  disable: true
} as ILogInForm

const service = new LogInService();
const LoginForm: FC<LoginFormProps> = ({ setFormName }) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [formValues, setFormValues] = useState<ILogInForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const isParent = formValues.name === CONTENTSNAME.LOGIN_OWNER;
  const isBabySitter = formValues.name === CONTENTSNAME.LOGIN_ACCESS;

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
    service.logIn(formValues).then(response => {
      setResult(response);
      if (response.data) {
        HttpService.setTokenToLocalStorage(response.data.token);
        dispatch(refreshToken(response.data.token));
        dispatch(setLogIn(response.data.createBy));
        setTimeout(() => {
          navigate('/home');
        }, 500);
      }
      else if (response.isError) {
        console.error(response.error);
        response.error = "Email et/ou mot de passe incorrect";
        setResult(response);
      }
    });
  }

  const changeForm = (name: string) => {
    if (formValues.name !== name) {
      setFormValues(values => ({ ...values, name }));
    }
  }

  return (
    <section className="LoginForm IndentificationForms" data-testid="LoginForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('login.authentication')}</h1>
      </div>
      <div className='row form-buttons'>
        <button
          className={isParent ? 'head-form-button active' : 'head-form-button'}
          onClick={() => changeForm(CONTENTSNAME.LOGIN_OWNER)}>{i18n.t("login.parent")}
        </button>
        <button
          className={isBabySitter ? 'head-form-button active' : 'head-form-button'}
          onClick={() => changeForm(CONTENTSNAME.LOGIN_ACCESS)}>{i18n.t("login.babysitter")}
        </button>
      </div>
      <form method='post' action='#' onSubmit={handleSubmit}>
        <div className='row'>
          {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
          {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
        </div>
        {isBabySitter &&
          <div className='row' id={CONTENTSNAME.LOGIN_ACCESS}>
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
        {isParent &&
          <div className='row' id={CONTENTSNAME.LOGIN_OWNER}>
            <div className='row input-row'>
              <input
                className='col-100'
                type="email"
                id="email"
                name="email"
                placeholder={i18n.t("login.typeEmail")}
                onChange={(event) => handleChange("email", event.target.value)}
                value={formValues.email}
                autoComplete="on" />
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
                onClick={() => setFormName(CONTENTSNAME.RECOVERY)}>{i18n.t("login.passwordForgotten")} ?
              </div>
            </div>
          </div>
        }
        <div className='row form-footer'>
          <SubmitButton
            label={i18n.t("login.connect")}
            isLoading={result.isLoading}
            isDisabled={formValues.disable} />
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => setFormName(CONTENTSNAME.SIGNIN)}>{i18n.t("login.noaccount")} ?
          </div>
        </div>
      </form>
    </section>
  );
}
export default LoginForm;