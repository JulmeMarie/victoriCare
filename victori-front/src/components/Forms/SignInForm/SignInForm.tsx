import React, { FC, useState } from 'react';
import i18n from '../../../utils/languages/I18N';
import '../Form.css';
import './SignInForm.css';
import { FaLock, FaUserCircle, FaEnvelope, FaSpinner } from 'react-icons/fa';
import Alert from '../../Alert/Alert';
import { IResult } from '../../../utils/global-interfaces';
import { SignInService } from '../../../services/SignInService';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import { defaultResult } from '../../../utils/global-default-values';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';

interface SignInFormProps {

}
export interface ISignInForm {
  email: string,
  password1: string,
  password2: string,
  disable: boolean,
  name: string
}

export const defaultValues = {
  email: "",
  password1: "",
  password2: "",
  disable: true,
  name: CONTENTSNAME.SIGNIN
} as ISignInForm

const service = new SignInService();

const SignInForm: FC<SignInFormProps> = () => {
  const [formValues, setFormValues] = useState<ISignInForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const handleChange = (key: string, value: any) => {
    const values = { ...formValues, [key]: value };
    service.validate(values);
    setFormValues(values);
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    setResult({ ...result, isLoading: true });
    service.signIn(formValues).then(response => {
      setResult(response);
      if (response.data) {
        setTimeout(() => {
          //setFormName(CONTENTSNAME.CODE);
        }, 500);
      }
      else if (response.isError) {
        console.error(response.error);
        //response.error = "Email et/ou mot de passe incorrect";
        setResult(response);
      }
    });
  }

  return (
    <div className="SignInForm Form" data-testid="SignInForm">
      <div className='row'>
        <h4 className='title form-title'><FaUserCircle /> {i18n.t('signin.register')}</h4>
      </div>
      <form method='post' action='#' onSubmit={(event) => handleSubmit(event)}>
        <div className='row'>
          {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
          {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
        </div>
        <div className='row input-row'>
          <input
            className='round-corner-input col-100'
            type="email"
            id="email_s"
            name="email"
            placeholder={i18n.t("signin.typeEmail")}
            onChange={(event) => handleChange("email", event.target.value)}
            value={formValues.email} />
          <FaEnvelope />
        </div>
        <div className='row input-row'>
          <input
            className='round-corner-input col-100'
            type="password"
            id="password1"
            name="password1"
            placeholder={i18n.t("signin.typePassword1")}
            value={formValues.password1}
            onChange={(event) => handleChange("password1", event.target.value)} />
          <FaLock />
        </div>
        <div className='row input-row'>
          <input
            className='round-corner-input col-100'
            type="password"
            id="password2"
            name="password2"
            placeholder={i18n.t("signin.checkPassword2")}
            value={formValues.password2}
            onChange={(event) => handleChange("password2", event.target.value)} />
          <FaLock />
        </div>
        <div className='row form-footer'>
          <SubmitButton
            label={i18n.t("signin.validate")}
            isLoading={result.isLoading}
            isDisabled={formValues.disable} />
        </div>
      </form>
    </div>);
}

export default SignInForm;