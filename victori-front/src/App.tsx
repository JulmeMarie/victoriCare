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
import Drawer from './components/Drawer/Drawer';
const App = () => {
  const dispatch = useDispatch<AppDispatch>();
  const lang = useSelector((state: AppState) => state.commonReducer.lang);

  console.log("Current language :", lang);

  useEffect(() => {
    dispatch(setScroll(window.scrollY));
    window.addEventListener('scroll', () => {
      dispatch(setScroll(window.scrollY));
    });
  });

  return (
    <div className='App'>
      <BrowserRouter>
        <Header />
        <Drawer />
        <Routing />
        <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;