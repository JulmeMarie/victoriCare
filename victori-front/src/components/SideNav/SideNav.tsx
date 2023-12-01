import React, { FC } from 'react';
import { FaTimes } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { setSideNavStatus } from '../../redux/reducers/app-reducer';
import { AppState } from '../../redux/store';
import './SideNav.css';

interface SideNavProps { }

const SideNav: FC<SideNavProps> = () => {
  const dispatch = useDispatch();
  const sideNavStatus = useSelector((state: AppState) => state.common_state.sideNaveStatus);

  const closeSideNav = () => {
    dispatch(setSideNavStatus(false));
  }

  return (
    <div className={"SideNav " + (sideNavStatus ? "open" : "close")} data-testid="SideNav">
      <div className='SideNave__close' onClick={() => closeSideNav()}><FaTimes /></div>
      <div className="SideNav__content" >
        Samano
      </div>
    </div>
  );
}

export default SideNav;
