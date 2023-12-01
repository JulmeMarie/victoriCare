import React, { FC } from 'react';
import './BabyList.css';

interface BabyListProps {}

const BabyList: FC<BabyListProps> = () => (
  <div className="BabyList" data-testid="BabyList">
    BabyList Component
  </div>
);

export default BabyList;
