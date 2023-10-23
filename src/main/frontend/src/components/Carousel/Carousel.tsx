import React, { FC } from 'react';
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
  return (
    <section className="Carousel" data-testid="Carousel">
      <div className='carousel-wrapper'>
        <div className='carousel-box'>
          <div className='carousel-items-box'>
            {carouselItems.map((item, index) =>
              <div key={index} className='carousel-item'>
                <h2 className='carousel-item__title'>{item.title1}</h2>
                <h1 className='carousel-item__title'>{item.title2}</h1>
                <div className='carousel-item__description'>{item.description}</div>
                <div className='carousel-link'>
                  <a href={item.link}> En savoir plus </a>
                  <a href='>/signin'> Inscrivez-vous </a>
                </div>
              </div>)}
          </div>
        </div>
        <div className='carousel-image-box'>
          <div className='carousel-baby-image'></div>
          <div className='carousel-image'></div>
        </div>
      </div>
    </section>
  );
}

export default Carousel;
