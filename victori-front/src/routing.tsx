import React from 'react';
import './index.css';
import { Routes, Route } from 'react-router-dom';
import Error404View from './views/Error404View/Error404View';
import IndexView from './views/IndexView/IndexView';
import HomeView from './views/HomeView/HomeView';
import { CONTENTSNAME } from './utils/Constants';
import CareList from './components/CareList/CareList';
import EventList from './components/EventList/EventList';

export const Routing = () => (
  <Routes>
    <Route path="/" element={<IndexView formName={CONTENTSNAME.LOGIN_OWNER} />} />
    <Route path="/login" element={<IndexView formName={CONTENTSNAME.LOGIN_OWNER} />} />
    <Route path="/signin" element={<IndexView formName={CONTENTSNAME.SIGNIN} />} />
    <Route path="/code/:user/:action" element={<IndexView formName={CONTENTSNAME.CODE} />} />

    <Route path="/home" element={<HomeView />} >
      <Route path="list/cares" element={<CareList />} />
      <Route path="list/events" element={<EventList />} />
      <Route path="list/names" element={<EventList />} />
      <Route path="list/suggestions" element={<EventList />} />
    </Route>
    <Route path="*" element={<Error404View />} />
  </Routes>
);

export default Routing;
