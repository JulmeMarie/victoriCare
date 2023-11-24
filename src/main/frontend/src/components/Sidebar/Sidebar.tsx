import React, { FC, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import LoginForm from '../Forms/LoginForm/LoginForm';
import RecoveryForm from '../Forms/RecoveryForm/RecoveryForm';
import SignInForm from '../Forms/SignInForm/SignInForm';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {

}

const formIds = {
  "login": "login-form",
  "recovery": "login-recovery-form",
  "signin": "signin-form"
}

const Sidebar: FC<SidebarProps> = () => {
  const [formId, setFormId] = useState<string>(formIds.login);
  return (
    <aside className="Sidebar">
      <section className="IndentificationForms">
        {formId === formIds.login && <LoginForm setFormId={setFormId} />}
        {formId === formIds.recovery && <RecoveryForm setFormId={setFormId} />}
        {formId === formIds.signin && <SignInForm setFormId={setFormId} />}
      </section>
    </aside>
  );
}

export default Sidebar;
