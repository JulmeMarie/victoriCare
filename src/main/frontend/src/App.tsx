import './App.css';
import { useEffect, useState } from 'react';
import { BrowserRouter } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import Sidebar from './components/Sidebar/Sidebar';
import { useSelector } from 'react-redux';
import { RootState } from './redux/store';
const App = () => {
  const isOnline = useSelector((state: RootState) => state.app_state.token);
  const lang = useSelector((state: RootState) => state.app_state.user.profil.lang);

  return (
    <BrowserRouter>
      <><Header />
        <main className='main-container'>
          <Routing />
          <Sidebar />
        </main>
        <Footer />
      </>
    </BrowserRouter>
  );
}

export default App;