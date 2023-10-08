import './App.css';
import { useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';
import Sidebar from './components/Sidebar/Sidebar';

const App = () => {

  useEffect(() => {

  }, []);

  return (
    <BrowserRouter>
      <Header />
      <main className='main-container'>
        <Routing />
        <Sidebar />
      </main>
      <Footer />
    </BrowserRouter>
  );
}

export default App;