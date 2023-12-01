import React, { FC, ReactNode } from 'react';
import { FaBaby, FaBirthdayCake, FaChild, FaAngleLeft, FaAngleRight, FaQuoteLeft } from 'react-icons/fa';
import { Service } from '../../views/IndexView/IndexView';
import './ServiceCard.css';

interface ServiceCardProps {
  service: Service
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
        {service.paragraphs.map((paragraph, index) => <p key={index} className='ServiceCard-paragraph'>{paragraph}</p>)}
      </div>
      <div className='samTest'>un test imprtant</div>

    </div>
  );
}

export default ServiceCard;
