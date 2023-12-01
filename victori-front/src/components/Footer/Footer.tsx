import React from 'react';
import './Footer.css';
import { Link } from 'react-router-dom';

const Footer = () => (
  <footer className="Footer">
    <ul className="Footer-menu">
      <li><Link to="/">copyright@victoriacare</Link></li><li><Link to="/">mentions légales</Link></li></ul>
  </footer>
);

export default Footer;
