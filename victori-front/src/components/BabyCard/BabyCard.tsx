import React, { FC } from 'react';
import './BabyCard.css';

interface BabyCardProps {}

const BabyCard: FC<BabyCardProps> = () => (
  <div className="BabyCard" data-testid="BabyCard">
    BabyCard Component
  </div>
);

export default BabyCard;
