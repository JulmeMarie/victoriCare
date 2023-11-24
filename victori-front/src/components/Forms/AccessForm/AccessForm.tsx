import React, { FC, useState } from 'react';
import './AccessForm.css';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { FaEnvelope, FaLock, FaUser, FaKey, FaSpinner } from 'react-icons/fa';
import { IResult } from '../../../utils/global-interfaces';
import HttpService from '../../../services/HttpService';
import { ALERTS, CONTENTSNAME } from '../../../utils/Constants';
import i18n from '../../../utils/languages/I18N';
import { setLogIn, refreshToken } from '../../../redux/reducers/app-reducer';
import './LoginForm.css';
import { defaultResult } from '../../../utils/global-default-values';
import Alert from '../../Alert/Alert';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';

interface AccessFormProps {

}

export interface IAcessForm {
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
} as IAcessForm;


const AccessForm: FC<AccessFormProps> = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [formValues, setFormValues] = useState<IAcessForm>(defaultValues);
  const [result, setResult] = useState<IResult>(defaultResult);

  const handleChange = (key: string, value: any) => {
    setFormValues(values => {
      const newVvalues = { ...values, [key]: value };
      //service.validate(newVvalues);
      return newVvalues;
    });
  }
  const handleSubmit = (event: any) => {
    event.preventDefault();
    setResult(result => ({ ...result, isLoading: true }));
    /*
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
    });*/
  }

  return (
    <div className="AccessForm Form" data-testid="AccessForm">

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
  );
}

export default AccessForm;