import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header = () => {

      const Menu = () => {
            return (<ul className="Header-menu">
                  <li><Link to="/"><i className="fa fa-home"></i> Accueil</Link> </li>
                  <li><Link to="/cares/current"><i className="fa fa-clock"></i> événements</Link> </li>
                  <li><Link to="/cares/past"><i className="fa fa-calendar"></i> Soins bébé</Link> </li>
            </ul>);
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
