import React, { FC } from 'react';
import { FaBuilding, FaEnvelope, FaLock, FaTimes, FaUser } from 'react-icons/fa';
import { useDispatch, useSelector } from 'react-redux';
import { setDrawerStatus } from '../../redux/reducers/app-reducer';
import { AppDispatch, AppState } from '../../redux/store';
import { LogIn } from '../../utils/global-interfaces';
import Language from '../DropDown/DropDown';
import './Drawer.css';

interface DrawerProps {

}

const Drawer: FC<DrawerProps> = () => {
  const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
  const dispatch = useDispatch<AppDispatch>();
  const sideNavStatus = useSelector((state: AppState) => state.commonReducer.sideNaveStatus);

  const closeDrawer = () => {
    dispatch(setDrawerStatus(false));
  }

  const getMobileMenu = () => {
    return <></>;
    /*
    return (
      logIn ? <>Online user</> : <ul className="Drawer-menu Drawer-menu__mobile">
        <li><div className='fa-icon'><FaLock /></div> <div className='menu-text'>Se connecter</div></li>
        <li><div className='fa-icon'><FaUser /></div> <div className='menu-text'> S'inscrire</div></li>
        <li><div className='fa-icon'><FaEnvelope /></div> <div className='menu-text'> Contactez-nous</div></li>
        <li><div className='fa-icon'><FaBuilding /></div> <div className='menu-text'> A propos</div></li>
       <li><Language /></li>
      </ul>
    )*/
  };

  return (
    <aside className={"Drawer " + (sideNavStatus ? "open" : "close")} data-testid="Drawer">
      <div className="Drawer__content" >
        <div className='Drawer__header'>
          <div className='logo-name Drawer-header__logo-name'></div>
          <FaTimes className='Drawer__close' onClick={() => closeDrawer()} />
        </div>
        <div className='Drawer__body'>
          <nav>
            {getMobileMenu()}
          </nav>
        </div>
      </div>
    </aside>
  );
}

export default Drawer;
