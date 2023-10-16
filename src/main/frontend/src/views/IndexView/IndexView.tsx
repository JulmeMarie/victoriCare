import React, { FC } from 'react';
import { Outlet } from 'react-router-dom';
import SignInForm from '../../components/SignInForm/SignInForm';
import './IndexView.css';

interface IndexViewProps { }

const IndexView: FC<IndexViewProps> = () => (
  <div className='IndexView' data-testid="IndexView">
    <section>
      <Outlet />
    </section>
    <section className='banner'>
      <img src={process.env.PUBLIC_URL + "/logo-t.png"} alt="" />
    </section>
    <section>
      <SignInForm />
    </section>
  </div>
);

export default IndexView;
