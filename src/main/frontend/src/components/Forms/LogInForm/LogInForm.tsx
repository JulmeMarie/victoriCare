import { LockFilled } from '@ant-design/icons';
import { FC, useState } from 'react';
import i18n from '../../../utils/languages/I18N';
import '../Form.css';
import './LogInForm.css';
import LoginOwnerForm from '../LoginOwnerForm/LoginOwnerForm';
import LoginAccessForm from '../LoginAccessForm/LoginAccessForm';
import RecoveryForm from '../RecoveryForm/RecoveryForm';

interface LogInFormProps { }
const formIds = {
    "owner": "login-owner-form",
    "access": "login-access-form",
    "recovery": "login-recovery-form"
}

const LogInForm: FC<LogInFormProps> = () => {
    const [formId, setFormId] = useState<string>(formIds.owner);

    return (
        <div className="LogInForm" data-testid="LogInForm">
            <div className='row'>
                <h3 className='form-title'><LockFilled /> {i18n.t('login.authentication')}</h3>
                <ul className='form-menu col-100'>
                    <li
                        className={formId === formIds.owner ? 'active col-50' : 'col-50'}
                        onClick={() => { setFormId(formIds.owner) }}> {i18n.t("login.yourAccount")} ?
                    </li>
                    <li
                        className={formId === formIds.access ? 'active col-50' : 'col-50'}
                        onClick={() => { setFormId(formIds.access) }}> {i18n.t("login.babySitter")} ?
                    </li>
                </ul>
            </div>
            {formId === formIds.owner && <LoginOwnerForm setFormId={setFormId} />}
            {formId === formIds.access && <LoginAccessForm />}
            {formId === formIds.recovery && <RecoveryForm />}
        </div>
    );
}
export default LogInForm;