import React, { FC, useState } from 'react';
import i18n from '../../../utils/languages/I18N';
import './RecoveryForm.css';

interface RecoveryFormProps { }

let iForm = {
  email: null,
  id: "login-recovery-form",
  disable: true
}

const RecoveryForm: FC<RecoveryFormProps> = () => {
  const [form, setForm] = useState(iForm);
  const [disable, setDisable] = useState<boolean>(true);

  const handleChange = (event: any) => {
    setForm({ ...form, [event.target.name]: event.target.value });
  }


  const handleSubmit = () => {

  }
  return (
    <div className="RecoveryForm" data-tested-id="RecoveryForm">
      <form method='post' action='#' id={iForm.id} onSubmit={handleSubmit}>
        <div className='row'>
          <label className='col-100' htmlFor="email"> {i18n.t("login.email")} :  </label>
          <input
            className='col-100'
            type="email" id="email"
            name="email"
            placeholder={i18n.t("login.typeEmail")} />
        </div>
        <div className='row form-footer'>
          <input type="submit" value={i18n.t("login.send")} disabled={disable} />
        </div>
      </form>
    </div>
  );
}

export default RecoveryForm;
