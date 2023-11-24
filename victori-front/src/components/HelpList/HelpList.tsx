import React, { FC } from 'react';
import './HelpList.css';
import { VICTORI_HELP } from '../../utils/static-data';
import HelpCard from '../HelpCard/HelpCard';

interface HelpListProps { }

export interface IHelp {
  text: string,
  image: string
}

const HelpList: FC<HelpListProps> = () => {

  return <article className="HelpList" data-testid="HelpList" id="about-us">
    <h1 className='title main-title'>{VICTORI_HELP.title} </h1>
    <section className='flex-column'>
      {
        VICTORI_HELP.contents.map((help: IHelp, index: number) =>
          <HelpCard key={index} help={help} />)
      }
    </section>
  </article>;
}

export default HelpList;
