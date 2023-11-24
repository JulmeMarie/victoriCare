import React, { FC } from 'react';
import i18n from '../../utils/languages/I18N';
import Language from '../DropDown/DropDown';
import Drawer from '../Drawer/Drawer';
import { setDrawerStatus } from '../../redux/reducers/app-reducer';
import { LogIn } from '../../utils/global-interfaces';
import NotificationList from '../NotificationList/NotificationList';
import DropDown from '../DropDown/DropDown';
import { LANGUAGES } from '../../utils/Constants';
import { FaBars, FaBell, FaBirthdayCake, FaChild, FaLock, FaPlus, FaSmile } from 'react-icons/fa';
import { IoMailSharp, IoHomeSharp, IoInformationCircle, IoGiftSharp, IoHappySharp } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AppDispatch, AppState } from '../../redux/store';

import './HeaderMenu.css';

interface HeaderMenuProps { }

const HeaderMenu: FC<HeaderMenuProps> = () => {
  const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
  const dispatch = useDispatch<AppDispatch>();
  const lang = i18n.langStr;

  const openDrawer = () => {
    dispatch(setDrawerStatus(true));
  }
  const getMobileMenu = () => {
    return (
      <ul className="Header-menu Header-menu__mobile">
        <li className='sidenav-toggle' onClick={() => openDrawer()}><FaBars /></li>
      </ul>
    )
  };

  const title = () => {
    return <>
      <div className='em-icon'><i className={"em em-" + lang}></i></div>
      <div className='menu-text'>{i18n.t("lang." + lang)}</div></>
  }
  const languages = () => {
    return Object.values(LANGUAGES).map((value, index) =>
      <> <div className='em-icon'><i className={"em em-" + value}></i></div> <div className='menu-text'> {i18n.t("lang." + value)}</div></>
    )
  }

  const getComputerMenu = () => {
    return (
      <ul className="Header-menu Header-menu__computer">
        <li className='active'>
          <Link to="/">
            <IoHomeSharp className='item-icon' />
            <span className='item-text'>{i18n.t("menu.home")}</span>
          </Link>
        </li>
        {
          logIn &&
          <>
            <li>
              <Link to="/home/list/events">
                <IoGiftSharp className='item-icon' />
                <span className='item-text'>{i18n.t("menu.events")}</span>
              </Link>
            </li>
            <li>
              <Link to="/home/list/cares">
                <IoHappySharp className='item-icon' />
                <span className='item-text'>{i18n.t("menu.babycare")}</span>
              </Link>
            </li>
            <li>
              <NotificationList />
            </li>
          </>
        }
      </ul>
    );
  }

  return (
    <div className="HeaderMenu" data-testid="HeaderMenu">
      HeaderMenu Component
    </div>
  );
}

export default HeaderMenu;
