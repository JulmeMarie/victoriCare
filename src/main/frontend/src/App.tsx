import './App.css';
import { useState, useEffect } from 'react';
import { BrowserRouter } from 'react-router-dom';
import TaskService from './services/TaskService';
import { Link } from 'react-router-dom';
import Routing from './routing';
import Footer from './components/Footer/Footer';
import Header from './components/Header/Header';

const App = () => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    /*
    const service = new TaskService();

    setTasks(service.list());
    console.table(service.list());*/
  }, []);

  return (
    <BrowserRouter>
      <Header />
      <main>
        <Routing />
      </main>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
