import React, { FC, useState } from 'react';
import { FaLock, FaEnvelope, FaSpinner } from 'react-icons/fa';
import { IResult } from '../../../utils/global-interfaces';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import i18n from '../../../utils/languages/I18N';
import { defaultResult } from '../../../utils/global-default-values';
import './CodeForm.css';
import { CodeService } from '../../../services/CodeService';
import { setUserAction } from '../../../redux/reducers/app-reducer';
import { useDispatch, useSelector } from 'react-redux';
import Alert from '../../Alert/Alert';
import { AppDispatch, AppState } from '../../../redux/store';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';
import Modal from '../../Modal/Modal';
import LoginForm from '../LoginForm/LoginForm';
import FormAlert from '../FormAlert/FormAlert';

interface CodeFormProps {

}
export interface ICodeForm {
  email: string,
  code: number,
  id: number,
  disable: boolean,
  name: string,
  color: string
}

export const defaultValues = {
  code: 0,
  id: 0,
  disable: true,
  name: CONTENTSNAME.CODE,
  color: "#fff"
} as ICodeForm

const service = new CodeService();

const CodeForm: FC<CodeFormProps> = () => {
  const dispatch = useDispatch<AppDispatch>();
  const userAction = useSelector((state: AppState) => state.commonReducer.userAction);
  const [formValues, setFormValues] = useState<ICodeForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

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

    if (userAction.name === "recovery") {
      service.recoverAccount(formValues).then(response => {
        setResult(response);
        if (response.data) {
          dispatch(setUserAction({ ...userAction, result: response }));
          setTimeout(() => {
            //setFormName(CONTENTSNAME.PASSWORD)
          }, 1000);
        }
        else if (response.isError) {
          console.error(response.error);
          response.error = "Une erreur s'est produite.";
          setResult(response);
        }
      });
    }
    else {
      service.validateAccount(formValues).then(response => {
        setResult(response);
        if (response.data) {
          dispatch(setUserAction({ ...userAction, result: response }));
          setTimeout(() => {
            //setFormName(CONTENTSNAME.LOGIN_OWNER)
          }, 1000);
        }
        else if (response.isError) {
          console.error(response.error);
          response.error = "Une erreur s'est produite.";
          setResult(response);
        }
      });
    }
  }
  return (
    <div className="CodeForm" data-tested-id="CodeForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('code.title')}</h1>
      </div>
      <form method='post' action='#' onSubmit={handleSubmit}>
        <FormAlert result={result} />
        <div className='row input-row'>
          <input
            className='col-100'
            type="number"
            id="code"
            name="code"
            placeholder={i18n.t("code.typeCode")}
            onChange={(event) => handleChange("code", event.target.value)}
            value={formValues.email} />
        </div>
        <div className='row input-row'>
          <input
            className='col-100'
            type="color"
            id="color"
            name="color"
            placeholder={i18n.t("code.typeCode")}
            onChange={(event) => handleChange("color", event.target.value)}
            value={formValues.color} />
        </div>
        <div className='row form-footer'>
          <SubmitButton
            label={i18n.t("code.send")}
            isLoading={result.isLoading}
            isDisabled={formValues.disable} />
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => <Modal><LoginForm /></Modal>}>{i18n.t("signin.havingaccount")}
          </div>
        </div>
      </form>
    </div>
  );
}

export default CodeForm;