import React, { FC, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import { FORMNAMES } from '../../utils/Constants';
import i18n from '../../utils/languages/I18N';
import CodeForm from '../Forms/CodeForm/CodeForm';
import LoginForm from '../Forms/LoginForm/LoginForm';
import PasswordForm from '../Forms/PasswordForm/PasswordForm';
import RecoveryForm from '../Forms/RecoveryForm/RecoveryForm';
import SignInForm from '../Forms/SignInForm/SignInForm';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {

}

const Sidebar: FC<SidebarProps> = () => {
  const [formName, setFormName] = useState<string>(FORMNAMES.LOGIN_OWNER);
  return (
    <aside className="Sidebar">
      <section className="IndentificationForms">
        {(formName === FORMNAMES.LOGIN_OWNER || formName === FORMNAMES.LOGIN_ACCESS) && <LoginForm setFormName={setFormName} />}
        {formName === FORMNAMES.RECOVERY && <RecoveryForm setFormName={setFormName} />}
        {formName === FORMNAMES.SIGNIN && <SignInForm setFormName={setFormName} />}
        {formName === FORMNAMES.CODE && <CodeForm setFormName={setFormName} />}
        {formName === FORMNAMES.PASSWORD && <PasswordForm setFormName={setFormName} />}
      </section>
    </aside>
  );
}

export default Sidebar;
