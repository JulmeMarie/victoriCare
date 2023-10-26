import { FC, ReactNode } from 'react';
import { FaBaby, FaBirthdayCake, FaChild } from 'react-icons/fa';
import { useOutlet } from 'react-router-dom';
import ServiceCard from '../../components/ServiceCard/ServiceCard';
import Sidebar from '../../components/Sidebar/Sidebar';
import './IndexView.css';

interface IndexViewProps { }

export interface Service {
  title: string,
  paragraphs: string[],
  icon: ReactNode
}

const servicesTab = [
  {
    title: "Arrivée du bébé",
    paragraphs: ["Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur.", "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur."],
    icon: <FaBaby />
  },
  {
    title: "Se faire garder son enfant",
    paragraphs: ["Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur."],
    icon: <FaChild />
  },
  {
    title: "Organiser des événéments",
    paragraphs: ["Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur.", "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur.", "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Labore distinctio fuga numquam delectus nisi atque at ea excepturi inventore beatae doloremque corporis alias, nihil omnis, est nulla iste blanditiis aspernatur."],
    icon: <FaBirthdayCake />
  }
];

const catchSetences = [
  {
    description: "Vous qui attendez un bébé, VicoriCare est le meilleur endroit pour vous aider à organiser l'arrivée de votre bébé notamment dans le choix du prénom, des événements qui précèdent l'arrive du bébé tels que le baby shower, les visites, etc...",
    img: "/img/new-born.jpg"
  },
  {
    description: "Vous qui etes parents, VictoriCare connaît vos inquiétudes quand vous confiez votre enfant à une tierce personne, ne serait-ce pour une jornée. C'est ppour cela, l'équipe VictoriCare tient à vous offrir cet outil vous permettant de suivre en temps réel les différents soins appliqués à votre enfant.",
    img: "/img/parent.jpg"
  },
  {
    description: "Vous qui organisez des événements socio-culturels où l'envoi d'une invitation est nécessaire et qu'une confrmation est requise, vous êtes au bon endroit. VictoriCare vous permet de gérer une de participants à un événément que vous organisez.",
    img: "/img/event.jpg"
  },
];

const IndexView: FC<IndexViewProps> = () => {
  const outlet = useOutlet();

  return (
    <main className='IndexView' data-testid="IndexView">
      <div className='First-content__container'>
        {
          outlet ||
          <article className='Catch-sentence__box'>
            <h1 className='title Catch-sentence__title'>COMMENT VICTORICARE VOUS AIDE ? </h1>
            {catchSetences.map((sentence, index) =>
              <section className='Catch-sentence__description' key={index}>
                <p>{sentence.description}</p>
                <figure className='Catch-sentence__image'>
                  <img src={process.env.PUBLIC_URL + sentence.img} alt={sentence.img} />
                </figure>
              </section>
            )}
          </article>
        }
        <Sidebar />
      </div>
      <article className='Second-content__container'>
        {servicesTab.map((serv, index) => <ServiceCard key={index} service={serv} />)}
      </article>
    </main>
  );
}

export default IndexView;
