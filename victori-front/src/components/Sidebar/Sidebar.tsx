import React, { FC, useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import { CONTENTSNAME } from '../../utils/Constants';
import { EContentNameType } from '../../utils/global-types';
import i18n from '../../utils/languages/I18N';
import CodeForm from '../Forms/CodeForm/CodeForm';
import LoginForm from '../Forms/LoginForm/LoginForm';
import PasswordForm from '../Forms/PasswordForm/PasswordForm';
import RecoveryForm from '../Forms/RecoveryForm/RecoveryForm';
import SignInForm from '../Forms/SignInForm/SignInForm';
import ProgressCareList from '../ProgressCareList/ProgressCareList';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {
  contentNameProp: EContentNameType
}

const Sidebar: FC<SidebarProps> = ({ contentNameProp }) => {
  const [contentName, setContentName] = useState<string>(contentNameProp);
  return (
    <aside className="Sidebar">
      {<LoginForm />}
      {contentName === CONTENTSNAME.RECOVERY && <RecoveryForm />}
      {contentName === CONTENTSNAME.SIGNIN && <SignInForm />}
      {contentName === CONTENTSNAME.CODE && <CodeForm />}
      {contentName === CONTENTSNAME.PASSWORD && <PasswordForm />}
      {contentName === CONTENTSNAME.PROGRESSCARES && <ProgressCareList />}
    </aside>
  );
}

export default Sidebar;
