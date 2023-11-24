import React, { FC, useState } from 'react';
import { FaLock, FaEnvelope } from 'react-icons/fa';
import { IResult } from '../../../utils/global-interfaces';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import i18n from '../../../utils/languages/I18N';
import { defaultResult } from '../../../utils/global-default-values';
import './RecoveryForm.css';
import { RecoveryService } from '../../../services/RecoveryService';
import { setUserAction } from '../../../redux/reducers/app-reducer';
import { useDispatch } from 'react-redux';
import Alert from '../../Alert/Alert';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';

interface RecoveryFormProps {

}
export interface IRecoveryForm {
  email: string,
  disable: boolean,
  name: string
}

export const defaultValues = {
  email: "",
  disable: true,
  name: CONTENTSNAME.RECOVERY
} as IRecoveryForm

const service = new RecoveryService();

const RecoveryForm: FC<RecoveryFormProps> = () => {
  const dispatch = useDispatch();
  const [formValues, setFormValues] = useState<IRecoveryForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const handleChange = (key: string, value: any) => {
    const values = { ...formValues, [key]: value };
    service.validate(values);
    setFormValues(values);
  }

  const handleSubmit = (event: any) => {
    event.preventDefault();
    setResult({ ...result, isLoading: true });
    service.recover(formValues).then(response => {
      setResult(response);
      if (response.data) {
        dispatch(setUserAction({ name: "recovery", result: response }));
        setTimeout(() => {
          //setFormName(CONTENTSNAME.CODE)
        }, 1000);
      }
      else if (response.isError) {
        console.error(response.error);
        response.error = "Une erreur s'est produite.";
        setResult(response);
      }
    });
  }
  return (
    <div className="RecoveryForm Form" data-tested-id="RecoveryForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('recovery.title')}</h1>
      </div>
      <form method='post' action='#' onSubmit={handleSubmit}>
        <div className='row'>
          {result.isError && <Alert message={result.error} showIcon type={ALERTS.ERROR} />}
          {result.data && <Alert message={result.success} showIcon type={ALERTS.SUCCESS} />}
        </div>
        <div className='row input-row'>
          <input
            className='round-corner-input col-100'
            type="email"
            id="email"
            name="email"
            placeholder={i18n.t("login.typeEmail")}
            onChange={(event) => handleChange("email", event.target.value)}
            value={formValues.email} />
          <FaEnvelope />
        </div>

        <div className='row form-footer'>
          <SubmitButton
            label={i18n.t("recovery.send")}
            isLoading={result.isLoading}
            isDisabled={formValues.disable} />
        </div>
      </form >
    </div >
  );
}

export default RecoveryForm;