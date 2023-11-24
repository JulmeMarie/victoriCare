import React, { FC } from 'react';
import './HelpCard.css';
import { IHelp } from '../HelpList/HelpList';

interface HelpProps {
  help: IHelp
}

const HelpCard: FC<HelpProps> = ({ help }) => (
  <div className="HelpCard flex-row" data-testid="HelpCare">
    <p>{help.text}</p>
    <figure>
      <img src={process.env.PUBLIC_URL + help.image} alt={help.image} />
    </figure>
  </div>
);

export default HelpCard;
