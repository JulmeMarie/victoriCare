import React, { FC } from 'react';
import { Outlet, useOutlet, useParams } from 'react-router-dom';
import Carousel from '../../components/Carousel/Carousel';
import ContactForm from '../../components/Forms/ContactForm/ContactForm';
import SignInForm from '../../components/Forms/SignInForm/SignInForm';
import './IndexView.css';

interface IndexViewProps { }

const IndexView: FC<IndexViewProps> = () => {
  const outlet = useOutlet();

  return (
    <div className='IndexView' data-testid="IndexView">
      {outlet || <Carousel />}
      <section className='banner'>
        <ContactForm />
      </section>
      <section>
        <SignInForm />
      </section>
    </div>
  );
}

export default IndexView;
