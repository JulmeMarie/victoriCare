import React, { FC, useEffect, useState } from 'react';
import i18n from '../../utils/languages/I18N';
import Tooltip from '../Tooltip/Tooltip';
import './Sidebar.css';

interface SidebarProps {

}

const Sidebar: FC<SidebarProps> = () => {
  return (
    <div className="Sidebar">
      {i18n.t("headerTitle", { appName: 'victoriCare', year: '2023' })}

    </div>
  );
}

export default Sidebar;
