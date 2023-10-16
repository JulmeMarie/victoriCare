import React, { FC } from 'react';
import './BabySitterList.css';

interface BabySitterListProps {}

const BabySitterList: FC<BabySitterListProps> = () => (
  <div className="BabySitterList" data-testid="BabySitterList">
    BabySitterList Component
  </div>
);

export default BabySitterList;
