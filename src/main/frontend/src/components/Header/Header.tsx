import { FC } from 'react';
import { IoMailSharp, IoHomeSharp, IoInformationCircle, IoGiftSharp, IoHappySharp } from "react-icons/io5";
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { RootState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import Carousel from '../Carousel/Carousel';
import Language from '../Language/Language';
import './Header.css';

interface HeaderProps {
    user?: any
}

const Header: FC<HeaderProps> = ({ user }) => {
    const Menu = () => {
        return user ? (
            <ul className="Header-menu">
                <li className='active'><Link to="/"><IoHomeSharp /> {i18n.t("menu.home")} </Link> </li>
                <li><Link to="/cares/current"><IoGiftSharp className='item-icon' /> <span className='item-text'>{i18n.t("menu.events")}</span></Link> </li>
                <li><Link to="/cares/past"><IoHappySharp /> {i18n.t("menu.babycare")}</Link> </li>
                <li><Language /></li>
            </ul>
        ) :
            (
                <ul className="Header-menu">
                    <li className='active'><Link to="/"><IoHomeSharp className='item-icon' /> <div className='item-text'>{i18n.t("menu.home")} </div></Link> </li>
                    <li><Link to="/cares/current"><IoInformationCircle /> {i18n.t("menu.about")}</Link> </li>
                    <li><Link to="/cares/past"><IoMailSharp /> {i18n.t("menu.contactUs")}</Link> </li>
                    <li><Language /></li>
                </ul>
            );
    }

    return (
        <header className="Header">
            <nav>
                <div className='wrapper-logo'>
                    <div className='Header-logo-image'></div>
                    <div className='Header-logo-name'></div>
                </div>
                <Menu />
            </nav>
            {!user && <Carousel />}
        </header>
    );
}
export default Header;
