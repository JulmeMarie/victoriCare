import './App.css';
import { useEffect, useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from './redux/store';
import { setScroll, initDevice } from './redux/reducers/app-reducer';
import { AppDispatch } from './redux/store';
import Sidebar from './components/Sidebar/Sidebar';
import SideNav from './components/SideNav/SideNav';
const App = () => {
  const isOnline = useSelector((state: AppState) => state.common_state.token);
  const lang = useSelector((state: AppState) => state.common_state.lang);
  const dispatch = useDispatch<AppDispatch>();

  useEffect(() => {
    dispatch(initDevice());
    dispatch(setScroll(window.pageYOffset));
    window.addEventListener('scroll', () => {
      dispatch(setScroll(window.pageYOffset));
    });
  });

  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <SideNav />
        <Routing />
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;