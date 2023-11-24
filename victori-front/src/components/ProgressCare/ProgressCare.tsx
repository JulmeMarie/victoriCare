import React, { FC } from 'react';
import { FaCircle, FaSmile } from 'react-icons/fa';
import { ICare } from '../../utils/global-interfaces';

import './ProgressCare.css';

interface ProgressCareProps {
  care?: ICare
}

const ProgressCare: FC<ProgressCareProps> = ({ care }) => (
  <div className="ProgressCare" data-testid="ProgressCare">
    <div className='ProgressCare__content'>
      <span className='fa-icon'><FaSmile /></span>
      <span className='menu-text>'> {care?.title} :  100% </span>
    </div>
    <div className='ProgressCare__color'></div>
  </div>
);

export default ProgressCare;
