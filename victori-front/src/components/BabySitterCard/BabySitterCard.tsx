import React, { FC } from 'react';
import './BabySitterCard.css';

interface BabySitterCardProps {}

const BabySitterCard: FC<BabySitterCardProps> = () => (
  <div className="BabySitterCard" data-testid="BabySitterCard">
    BabySitterCard Component
  </div>
);

export default BabySitterCard;
