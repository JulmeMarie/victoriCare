import React, { FC } from 'react';
import './AboutView.css';

interface AboutViewProps { }

const AboutView: FC<AboutViewProps> = () => {

  return (
    <article className="AboutView" data-testid="AboutView">
      <h1 className='title content-title'>Qui sommes nous ? </h1>
      <section>
        <p>
          VictoriCare est outil de gestion des événéments (Naissance, BabyShower, Anniversaire, Mariage, etc..) et des soins appliqués aux bébé et enfants.
        </p>
        <p>
          Cet outil se présente sous format web via le lien wwww.victoricare.fr et sur les plate-forme android et iOS sous le nom de VictoriCare.
        </p>
        <p>
          Simple d'utilisation, elle apporte une aide exceptionnelle pour l'arrivée des nouveaux-nés, et assure une bonne gestion de suivis de soins appliqués aux bébé et enfants.
        </p>
        <p>
          Parents, vous confiez votre enfant à une tierce personne, pas d'inquiétude, victoricare vous fait vivre en temps réel les soins appliqués à votre enfant.
        </p>
      </section>
    </article>
  );
}

export default AboutView;
