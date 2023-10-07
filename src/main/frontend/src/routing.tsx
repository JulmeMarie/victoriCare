import React from 'react';
import './index.css';
import { Routes, Route } from 'react-router-dom';
import Error404View from './views/Error404View/Error404View';
import IndexView from './views/IndexView/IndexView';

const Routing = () => (
  <Routes>
    <Route path="/" element={<IndexView />} />
    <Route path="*" element={<Error404View />} />
  </Routes>
);

export default Routing;

/*
<Route path="/task/:id" element={<TaskPage />} />
    <Route path="/care/start/:id" element={<CarePage action="start" />} />
    <Route path="task/update/:id" element={<TaskFormPage />} />
    <Route path="task/create" element={<TaskFormPage />} />
    <Route path="/cares/:action" element={<CarePage />} />
*/