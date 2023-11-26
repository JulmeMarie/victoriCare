import React, { FC, useState } from 'react';
import { FaLock, FaEnvelope } from 'react-icons/fa';
import { FormType } from '../../../utils/Enums';
import i18n from '../../../utils/languages/I18N';
import './RecoveryForm.css';

interface RecoveryFormProps {
  setFormId: (id: string) => void,
}

let iForm = {
  email: null,
  id: "login-recovery-form",
  disable: true
}

const RecoveryForm: FC<RecoveryFormProps> = ({ setFormId }) => {
  const [form, setForm] = useState(iForm);
  const [disable, setDisable] = useState<boolean>(true);

  const handleChange = (event: any) => {
    setForm({ ...form, [event.target.name]: event.target.value });
  }


  const handleSubmit = () => {

  }
  return (
    <div className="RecoveryForm" data-tested-id="RecoveryForm">
      <div className='row'>
        <h1 className='title form-title'><FaLock /> {i18n.t('recovery.title')}</h1>
      </div>
      <form method='post' action='#' id={iForm.id} onSubmit={handleSubmit}>
        <div className='row input-row'>
          <input
            className='col-100'
            type="email" id="email"
            name="email"
            placeholder={i18n.t("login.typeEmail")} />
          <FaEnvelope />
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.send")} disabled={disable} />
        </div>
        <div className='row'>
          <div
            className='form-link text-center'
            onClick={() => { setFormId(FormType.OWNER) }}>{i18n.t("signin.havingaccount")}
          </div>
        </div>
      </form>
    </div>
  );
}

export default RecoveryForm;
