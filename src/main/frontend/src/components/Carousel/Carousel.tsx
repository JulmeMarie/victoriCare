import React, { FC } from 'react';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import './Carousel.css';
interface CarouselProps { }

const carouselItems = [
  {
    title1: "Gestion De l'arivée",
    title2: "de votre bébé",
    description: "Vous pouvez d'ores et déjà réfléchir à des prénoms pour votre petit protégé. Il est également temps de réaliser votre entretien prénatal précoce avec votre médecin ou votre sage-femme.",
    link: "/manage-birth"
  },
  {
    index: 2,
    title1: "Gestion des soins",
    title2: "pour votre bébé",
    description: "dadzdddddddddddddddd",
    link: "/manage-care"
  },
  {
    title1: "Gestion des événements",
    title2: "Pour vos enfants et vous",
    description: "dzdzdddddddddddddddddddddddddddddd",
    link: "/manage-event"
  },
  {
    title1: "Gestion De l'arivée",
    title2: "de votre bébé",
    description: "Vous pouvez d'ores et déjà réfléchir à des prénoms pour votre petit protégé. Il est également temps de réaliser votre entretien prénatal précoce avec votre médecin ou votre sage-femme.",
    link: "/manage-birth"
  }
]

const Carousel: FC<CarouselProps> = () => {
  const scroll = useSelector((state: RootState) => state.app_state.scroll);
  const maxHeight = 250;
  const minHeight = 150;

  return (
    <section className={scroll >= minHeight ? "Carousel hide" : "Carousel show"} data-testid="Carousel" style={{ height: (maxHeight - scroll) + 'px' }}>
      <div className='Carousel-wrapper'>
        <div className='Carousel-box'>
          <div className='Carousel-items'>
            {carouselItems.map((item, index) =>
              <div key={index} className='Carousel-item'>
                <h2 className='Carousel-item__title'>{item.title1}</h2>
                <h1 className='Carousel-item__title'>{item.title2}</h1>
                <div className='Carousel-item__description'>{item.description}</div>
                <div className='Carousel-item__link'>
                  <a href={item.link}> En savoir plus </a>
                  <a href='>/signin'> Inscrivez-vous </a>
                </div>
              </div>)}
          </div>
        </div>
      </div>
    </section>
  );
}

export default Carousel;
