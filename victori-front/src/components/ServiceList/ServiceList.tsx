import React, { FC } from 'react';
import './ServiceList.css';
import { VICTORI_SERVICE } from '../../utils/static-data';
import ServiceCard from '../ServiceCard/ServiceCard';


export interface IService {
  title: string,
  paragraphs: Array<string>,
  icon: string
}

interface ServiceListProps { }

const ServiceList: FC<ServiceListProps> = () => {


  return (
    <section className="ServiceList" data-testid="ServiceList">
      <h1 className='title main-title'>{VICTORI_SERVICE.title} </h1><hr />
      <div className='ServiceList-wrapper flex-row'>
        {
          VICTORI_SERVICE.contents.map((serv: IService, index: number) =>
            <ServiceCard key={index} service={serv} />)
        }
      </div>
    </section>
  );
}

export default ServiceList;
