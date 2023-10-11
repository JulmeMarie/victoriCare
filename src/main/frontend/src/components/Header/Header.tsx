import { HomeFilled, CalendarFilled, SmileFilled } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import i18n from '../../utils/languages/I18N';
import Language from '../Language/Language';
import './Header.css';

const Header = () => {
    const Menu = () => {
        return (
            <ul className="Header-menu">
                <li className='active'><Link to="/"><HomeFilled /> {i18n.t("menu.home")} </Link> </li>
                <li><Link to="/cares/current"><CalendarFilled /> {i18n.t("menu.events")}</Link> </li>
                <li><Link to="/cares/past"><SmileFilled /> {i18n.t("menu.babycare")}</Link> </li>
                <li><Language /></li>
            </ul>
        );
    }

    return (
        <header className="Header">
            <div className='wrapper-logo'>
                <img src="/logo.png" alt="logo de victoriacare" />
                <div className="Header-app-name">VictoriCare</div>
            </div>
            <Menu />
        </header>
    );
}
export default Header;
