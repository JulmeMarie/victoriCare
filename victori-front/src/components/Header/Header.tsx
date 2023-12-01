import { FC } from 'react';
import { FaBars } from 'react-icons/fa';
import { IoMailSharp, IoHomeSharp, IoInformationCircle, IoGiftSharp, IoHappySharp } from "react-icons/io5";
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AppState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import Carousel from '../Carousel/Carousel';
import Language from '../Language/Language';
import SideNav from '../SideNav/SideNav';
import { setSideNavStatus } from '../../redux/reducers/app-reducer';
import './Header.css';

interface HeaderProps {
    user?: any
}

const Header: FC<HeaderProps> = ({ user }) => {
    const dispatch = useDispatch();
    const device = useSelector((state: AppState) => state.common_state.device);

    const openSideNav = () => {
        dispatch(setSideNavStatus(true));
    }

    return (
        <header className="Header">
            <nav>
                <div className='wrapper-logo'>
                    <div className='Header-logo-image'></div>
                    <div className='Header-logo-name'></div>
                </div>
                <ul className="Header-menu">
                    <li className='sidenav-toggle' onClick={() => openSideNav()}><FaBars /></li>
                    {device === 0 &&

                        <><li className='active'><Link to="/"><IoHomeSharp /> {i18n.t("menu.home")} </Link> </li>
                            {
                                user && <>
                                    <li><Link to="/cares/current"><IoGiftSharp className='item-icon' /> <span className='item-text'>{i18n.t("menu.events")}</span></Link> </li>
                                    <li><Link to="/cares/past"><IoHappySharp /> {i18n.t("menu.babycare")}</Link> </li>
                                </>
                            }
                            <li><Language /></li>
                        </>}
                </ul>
            </nav>
            {!user && <Carousel />}
        </header>
    );
}
export default Header;
