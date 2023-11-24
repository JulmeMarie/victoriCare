import { FC } from 'react';
import { FaBirthdayCake, FaChild, FaLock, FaPlus, FaSmile } from 'react-icons/fa';
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AppState } from '../../redux/store';

import HeaderMenu from '../HeaderMenu/HeaderMenu';
import Carousel from '../Carousel/Carousel';
import './Header.css';

interface HeaderProps {

}

const Header: FC<HeaderProps> = () => {
    const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
    const scroll = useSelector((state: AppState) => state.commonReducer.scroll);
    const maxHeight = logIn ? 150 : 250;
    const minHeight = logIn ? 100 : 150;


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
                <HeaderMenu />
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
