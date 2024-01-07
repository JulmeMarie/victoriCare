import './App.css';
import { useEffect, useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from './redux/store';
import { setScroll } from './redux/reducers/app-reducer';
import { AppDispatch } from './redux/store';
import Sidebar from './components/Sidebar/Sidebar';
import Drawer from './components/Drawer/Drawer';
import { ELanguageType } from './utils/global-types';
const App = () => {
  const dispatch = useDispatch<AppDispatch>();
  const logIn = useSelector((state: AppState) => state.common_state.logIn);
  const lang = useSelector((state: AppState) => state.common_state.lang);

  useEffect(() => {
    dispatch(setScroll(window.pageYOffset));
    window.addEventListener('scroll', () => {
      dispatch(setScroll(window.pageYOffset));
    });
  });

  return (
    <div className='App'>
      <BrowserRouter>
        <Header logIn={logIn} />
        <Drawer logIn={logIn} />
        <Routing logIn={logIn} />
        <Footer logIn={logIn} />
      </BrowserRouter>
    </div>
  );
}

export default App;