import './App.css';
import { useEffect, useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './redux/store';
import { setScroll } from './redux/reducer';
import { AppDispatch } from './redux/store';
import Sidebar from './components/Sidebar/Sidebar';
const App = () => {
  const isOnline = useSelector((state: RootState) => state.app_state.token);
  const lang = useSelector((state: RootState) => state.app_state.user.profil.lang);
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    dispatch(setScroll(window.pageYOffset));
    window.addEventListener('scroll', () => {
      dispatch(setScroll(window.pageYOffset));
    });
  });

  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <Routing />
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;