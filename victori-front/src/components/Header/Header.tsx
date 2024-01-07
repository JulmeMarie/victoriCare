import { FC } from 'react';
import { FaBars } from 'react-icons/fa';
import { IoMailSharp, IoHomeSharp, IoInformationCircle, IoGiftSharp, IoHappySharp } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AppDispatch, AppState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import Carousel from '../Carousel/Carousel';
import Language from '../Language/Language';
import Drawer from '../Drawer/Drawer';
import { setDrawerStatus } from '../../redux/reducers/app-reducer';
import './Header.css';
import { LogIn } from '../../utils/global-interfaces';

interface HeaderProps {
    logIn: LogIn | null
}

const Header: FC<HeaderProps> = ({ logIn }) => {
    const dispatch = useDispatch<AppDispatch>();

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

    const getComputerMenu = () => {
        return (
            <ul className="Header-menu Header-menu__computer">
                <li className='active'><Link to="/"><IoHomeSharp /> {i18n.t("menu.home")} </Link> </li>
                {
                    logIn &&
                    <>
                        <li><Link to="/cares/current"><IoGiftSharp className='item-icon' /> <span className='item-text'>{i18n.t("menu.events")}</span></Link> </li>
                        <li><Link to="/cares/past"><IoHappySharp /> {i18n.t("menu.babycare")}</Link> </li>
                    </>
                }
                <li><Language /></li>
            </ul>
        );
    }

    return (
        <header className="Header">
            <nav>
                <div className='wrapper-logo'>
                    <div className='Header-logo-image'></div>
                    <div className='logo-name Header-logo-name'></div>
                </div>
                {getMobileMenu()}
                {getComputerMenu()}
            </nav>
            {!logIn && <Carousel />}
        </header>
    );
}
export default Header;
