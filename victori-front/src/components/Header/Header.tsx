import { FC } from 'react';
import { FaBars, FaBell, FaBirthdayCake, FaChild, FaLock, FaPlus, FaSmile } from 'react-icons/fa';
import { IoMailSharp, IoHomeSharp, IoInformationCircle, IoGiftSharp, IoHappySharp } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AppDispatch, AppState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import Carousel from '../Carousel/Carousel';
import Language from '../DropDown/DropDown';
import Drawer from '../Drawer/Drawer';
import { setDrawerStatus } from '../../redux/reducers/app-reducer';
import './Header.css';
import { LogIn } from '../../utils/global-interfaces';
import NotificationList from '../NotificationList/NotificationList';
import DropDown from '../DropDown/DropDown';
import { LANGUAGES } from '../../utils/Constants';

interface HeaderProps {

}

const Header: FC<HeaderProps> = () => {
    const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
    const scroll = useSelector((state: AppState) => state.commonReducer.scroll);
    const maxHeight = logIn ? 150 : 250;
    const minHeight = logIn ? 100 : 150;
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
                <li><DropDown title={title()} items={languages()} /></li>
            </ul>
        );
    }

    const getActionButtons = () => {
        return (
            <div className='Header__action-buttons'>
                <button className='create-event-button'>
                    <FaBirthdayCake className='action-button__icon' />
                    <div>
                        <FaPlus className='fa-icon' />
                        <span className='menu-text'>Créer événement</span>
                    </div>
                </button>

                <button className='suggest-firstname-button'>
                    <FaChild className='action-button__icon' />
                    <div>
                        <FaPlus className='fa-icon' />
                        <span className='menu-text'>Proposer prénom</span>
                    </div>
                </button>

                <button className='create-access-button'>
                    <FaLock className='action-button__icon' />
                    <div>
                        <FaPlus className='fa-icon' />
                        <span className='menu-text'>Créer accès</span>
                    </div>
                </button>

                <button className='create-care-button'>
                    <FaSmile className='action-button__icon' />
                    <div>
                        <FaPlus className='fa-icon' />
                        <span className='menu-text'>Créer soin</span>
                    </div>
                </button>
            </div>
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
            <section className={`Header__main-content ${(scroll >= minHeight) ? "hide" : "show"}`} data-testid="Carousel" style={{ height: (maxHeight - scroll) + 'px' }}>
                <div className='main-content__wrapper'>
                    {!logIn && <Carousel />}
                    {logIn && getActionButtons()}
                </div>
            </section>
        </header>
    );
}
export default Header;
