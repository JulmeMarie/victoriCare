import React, { FC, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import LoginAccessForm from '../Forms/LoginAccessForm/LoginAccessForm';
import LoginOwnerForm from '../Forms/LoginOwnerForm/LoginOwnerForm';
import RecoveryForm from '../Forms/RecoveryForm/RecoveryForm';
import SignInForm from '../Forms/SignInForm/SignInForm';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {

}

const formIds = {
  "owner": "login-owner-form",
  "access": "login-access-form",
  "recovery": "login-recovery-form",
  "signin": "signin-form"
}

const Sidebar: FC<SidebarProps> = () => {
  const [formId, setFormId] = useState<string>(formIds.owner);
  return (
    <aside className="Sidebar">
      <section className="IndentificationForms">
        {formId === formIds.owner && <LoginOwnerForm setFormId={setFormId} />}
        {formId === formIds.access && <LoginAccessForm setFormId={setFormId} />}
        {formId === formIds.recovery && <RecoveryForm setFormId={setFormId} />}
        {formId === formIds.signin && <SignInForm setFormId={setFormId} />}
      </section>
    </aside>
  );
}

export default Sidebar;
