import React, { FC, useState } from 'react';
import i18n from '../../../utils/languages/I18N';
import '../Form.css';
import './PasswordForm.css';
import { FaLock, FaSpinner } from 'react-icons/fa';
import Alert from '../../Alert/Alert';
import { IResult } from '../../../utils/global-interfaces';
import { PasswordService } from '../../../services/PasswordService';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import { defaultResult } from '../../../utils/global-default-values';
import Modal from '../../Modal/Modal';
import SignInForm from '../SignInForm/SignInForm';

interface PasswordFormProps {

}
export interface IPasswordForm {
  password1: string,
  password2: string,
  disable: boolean,
  name: string
}

export const defaultValues = {
  password1: "",
  password2: "",
  disable: true,
  name: CONTENTSNAME.PASSWORD
} as IPasswordForm

const service = new PasswordService();

const PasswordForm: FC<PasswordFormProps> = () => {
  const [formValues, setFormValues] = useState<IPasswordForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const handleChange = (key: string, value: any) => {
    const values = { ...formValues, [key]: value };
    service.validate(values);
    setFormValues(values);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    setResult({ ...result, isLoading: true });
    service.reset(formValues).then(response => {
      setResult(response);
      if (response.data) {
        setTimeout(() => {
          //setFormName("login-owner-form");
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
    <div className="PasswordForm" data-testid="PasswordForm">
      <div className='row'>
        <h4 className='form-title'><FaLock /> {i18n.t('password.title')}</h4>
      </div>
      <form method='post' action='#' onSubmit={(event) => handleSubmit(event)}>
        <div className='row'>
          {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
          {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
        </div>
        <div className='row input-row'>
          <input
            className='col-100'
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
            className='col-100'
            type="password"
            id="password2"
            name="password2"
            placeholder={i18n.t("signin.checkPassword2")}
            value={formValues.password2}
            onChange={(event) => handleChange("password2", event.target.value)} />
          <FaLock />
        </div>
        <div className='row form-footer'>
          <button
            type='submit'
            className={result.isLoading ? 'loading-button' : 'submit-button'}
            disabled={formValues.disable || result.isLoading}
          >
            {result.isLoading ? <FaSpinner /> : i18n.t("password.send")}
          </button>
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => <Modal><SignInForm /></Modal>}>{i18n.t("signin.havingaccount")}
          </div>
        </div>
      </form>
    </div>);
}


export default PasswordForm;
