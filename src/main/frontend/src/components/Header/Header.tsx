import React from 'react';
import {Link} from 'react-router-dom';
import './Header.css';

const Header = () => (
  <header className="Header">
        <img src="/logo.png" alt="logo de victoriacare" />
        <div className="Header-name">VictoriCare</div>
        <ul className="Header-menu">
              <li><Link to="/"><i className="fa fa-home"></i> Accueil</Link> </li>
              <li><Link to="/cares/current"><i className="fa fa-clock"></i> Mes soins en cours</Link> </li>
              <li><Link to="/cares/past"><i className="fa fa-calendar"></i> Mes soins passés</Link> </li>
          </ul> 
    </header>
);
export default Header;
