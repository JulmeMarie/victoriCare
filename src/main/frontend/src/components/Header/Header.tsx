import { HomeFilled, CalendarFilled, SmileFilled } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import Language from '../Language/Language';
import './Header.css';

const Header = () => {
    const Menu = () => {
        return (
            <ul className="Header-menu">
                <li className='active'><Link to="/"><HomeFilled /> Accueil</Link> </li>
                <li><Link to="/cares/current"><CalendarFilled /> événements</Link> </li>
                <li><Link to="/cares/past"><SmileFilled /> Soins bébé</Link> </li>
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
