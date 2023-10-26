import React, { FC } from 'react';
import { Service } from '../../views/IndexView/IndexView';
import './ServiceCard.css';

interface ServiceCardProps {
  service: Service
}

const ServiceCard: FC<ServiceCardProps> = ({ service }) => (
  <div className="ServiceCard" data-testid="ServiceCard">
    <div className='ServiceCard-icon'>
      {service.icon}
    </div>
    <div className='ServiceCard-content'>
      <h1 className='title ServiceCard-title'>
        {service.title}
      </h1>
      {service.paragraphs.map((paragraph, index) => <p key={index} className='ServiceCard-paragraph'>{paragraph}</p>)}
    </div>
    <div className='samTest'>un test imprtant</div>

  </div>
);

export default ServiceCard;
