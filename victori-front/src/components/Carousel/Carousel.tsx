import React, { FC } from 'react';
import { useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import I18N from '../../utils/languages/I18N';
import './Carousel.css';
interface CarouselProps { }

export interface ICarousel {
  title1: string,
  title2: string,
  description: string
}

const Carousel: FC<CarouselProps> = () => {
  const carouselItems = I18N.t("carousel") as Array<ICarousel>;

  return (
    <div className='Carousel'>
      <div className='Carousel-items'>
        {carouselItems.map((item, index) =>
          <div key={index} className='Carousel-item'>
            <h2 className='Carousel-item__title'>{item.title1}</h2>
            <h1 className='Carousel-item__title'>{item.title2}</h1>
            <div className='Carousel-item__description-wrapper'>
              <div className='Carousel-item__description'>{item.description}</div>
            </div>
            <div className='Carousel-item__link'>
              <a href="#about-us"> En savoir plus </a>
              <a href='/signin'> Inscrivez-vous </a>
            </div>
          </div>)}
      </div>
    </div>
  );
}

export default Carousel;
