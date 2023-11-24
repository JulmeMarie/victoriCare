import React, { FC } from 'react';
import { FaBaby, FaBirthdayCake, FaChild } from 'react-icons/fa';

import './ServiceCard.css';
import { IService } from '../ServiceList/ServiceList';

interface ServiceCardProps {
  service: IService
}

const icons = [
  { key: 'FaBaby', value: <FaBaby /> },
  { key: 'FaChild', value: <FaChild /> },
  { key: 'FaBirthdayCake', value: <FaBirthdayCake /> }
]

const ServiceCard: FC<ServiceCardProps> = ({ service }) => {

  const getIcon = (iconString: string) => {
    return icons.find(icon => icon.key === iconString);
  }

  return (
    <div className="ServiceCard" data-testid="ServiceCard">
      <div className='ServiceCard-icon'>
        {getIcon(service.icon)?.value}
      </div>
      <div className='ServiceCard-content'>
        <h2 className='title ServiceCard-title'>
          {service.title}
        </h2>
        <div className='ServiceCard-wrapper'>
          {service.paragraphs.map((paragraph, index) => <p key={index} className='ServiceCard-paragraph'>{paragraph}</p>)}
        </div>
      </div>
    </div>
  );
}

export default ServiceCard;
