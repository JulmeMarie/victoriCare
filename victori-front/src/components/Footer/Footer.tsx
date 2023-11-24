import React, { FC } from 'react';
import './Footer.css';
import { Link } from 'react-router-dom';
import { LogIn } from '../../utils/global-interfaces';
import ContactForm from '../Forms/ContactForm/ContactForm';
interface FooterProps {

}

const Footer: FC<FooterProps> = () => (
  <footer className="Footer flex-column">
    <div className='Footer-top'>
      <ContactForm />
    </div>
    <div className='Footer-bottom'>
      <ul className="Footer-menu flex-row">
        <li><Link to="/">copyright@victoriacare</Link></li><li><Link to="/">mentions légales</Link></li>
      </ul>
    </div>
  </footer>
);

export default Footer;
