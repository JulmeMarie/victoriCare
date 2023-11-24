import React, { FC } from 'react';
import './BabyDetails.css';

interface BabyDetailsProps {}

const BabyDetails: FC<BabyDetailsProps> = () => (
  <div className="BabyDetails" data-testid="BabyDetails">
    BabyDetails Component
  </div>
);

export default BabyDetails;
