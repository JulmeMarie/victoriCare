import React, { FC, ReactElement, useState } from 'react';
import './LoginForm.css';
import SubmitButton from '../Fields/SubmitButton/SubmitButton';
import Modal from '../../Modal/Modal';
import SignInForm from '../SignInForm/SignInForm';
import RecoveryForm from '../RecoveryForm/RecoveryForm';
import { IoPerson } from 'react-icons/io5';
import EmailField from '../Fields/EmailField/EmailField';
import PasswordInput from '../Fields/PasswordInput/PasswordInput';
import FormAlert from '../FormAlert/FormAlert';
import useLoginForm from '../../../customHooks/useLoginForm';
import { LABELS } from '../../../utils/Constants';

interface LoginFormProps { }

const LoginForm: FC<LoginFormProps> = () => {
  const [modal, setModal] = useState<ReactElement | null>();
  const { values, result, handleChange, handleSubmit } = useLoginForm();

  const initModal = (element: ReactElement) => {
    setModal(<Modal onClose={() => setModal(null)}>{element}</Modal>)
  }

  return (
    <div className="LoginForm Form" data-testid="LoginForm">
      <div className='Form-main-icon'>
        <IoPerson />
      </div>
      <form method='post' action='#' onSubmit={handleSubmit}>
        <FormAlert result={result} />
        <EmailField onChange={handleChange} defaultValue={values.email} />
        <PasswordInput onChange={handleChange} defaultValue={values.password} />

        <div
          className='form-link'
          onClick={() => initModal(<RecoveryForm />)}>{LABELS.passwordForgotten} ?
        </div>
        <SubmitButton
          label={LABELS.toLogIn}
          isLoading={result.isLoading}
          isDisabled={values.disable} />

        <div
          className='form-link text-center'
          onClick={() => initModal(<SignInForm />)}>
          {LABELS.noAccountSubscribe} ?
        </div>
      </form>
      {modal}
    </div>
  );
}
export default LoginForm;