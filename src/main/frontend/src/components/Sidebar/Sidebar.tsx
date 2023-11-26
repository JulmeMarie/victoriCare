import React, { FC, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import { FormType } from '../../utils/Enums';
import i18n from '../../utils/languages/I18N';
import LoginForm from '../Forms/LoginForm/LoginForm';
import RecoveryForm from '../Forms/RecoveryForm/RecoveryForm';
import SignInForm from '../Forms/SignInForm/SignInForm';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {

}

const Sidebar: FC<SidebarProps> = () => {
  const [formId, setFormId] = useState<string>(FormType.OWNER);
  return (
    <aside className="Sidebar">
      <section className="IndentificationForms">
        {(formId === FormType.OWNER || formId === FormType.ACCESS) && <LoginForm setFormId={setFormId} />}
        {formId === FormType.RECOVERY && <RecoveryForm setFormId={setFormId} />}
        {formId === FormType.SIGNIN && <SignInForm setFormId={setFormId} />}
      </section>
    </aside>
  );
}

export default Sidebar;
