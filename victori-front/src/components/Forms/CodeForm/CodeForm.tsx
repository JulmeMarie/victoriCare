import React, { FC, useState } from 'react';
import { FaLock, FaEnvelope, FaSpinner } from 'react-icons/fa';
import { IResult } from '../../../utils/global-interfaces';
import { ALERTS, FORMNAMES } from '../../../utils/Constants';
import i18n from '../../../utils/languages/I18N';
import { defaultResult } from '../../../utils/global-default-values';
import './CodeForm.css';
import { CodeService } from '../../../services/CodeService';
import { setUserAction } from '../../../redux/reducers/app-reducer';
import { useDispatch, useSelector } from 'react-redux';
import Alert from '../../Alert/Alert';
import { AppDispatch, AppState } from '../../../redux/store';
import SubmitButton from '../SubmitButton/SubmitButton';

interface CodeFormProps {
  setFormName: (id: string) => void,
}
export interface ICodeForm {
  email: string,
  code: number,
  id: number,
  disable: boolean,
  name: string
}

export const defaultValues = {
  code: 0,
  id: 0,
  disable: true,
  name: FORMNAMES.CODE,
} as ICodeForm

const service = new CodeService();

const CodeForm: FC<CodeFormProps> = ({ setFormName }) => {
  const dispatch = useDispatch<AppDispatch>();
  const userAction = useSelector((state: AppState) => state.common_state.userAction);
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
            setFormName(FORMNAMES.PASSWORD)
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
            setFormName(FORMNAMES.LOGIN_OWNER)
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
        <div className='row'>
          {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
          {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
        </div>
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
        <div className='row form-footer'>
          <SubmitButton
            label={i18n.t("code.send")}
            isLoading={result.isLoading}
            isDisabled={formValues.disable} />
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => { setFormName(FORMNAMES.LOGIN_OWNER) }}>{i18n.t("signin.havingaccount")}
          </div>
        </div>
      </form>
    </div>
  );
}

export default CodeForm;