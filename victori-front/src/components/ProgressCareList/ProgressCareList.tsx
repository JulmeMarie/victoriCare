import React, { FC } from 'react';
import { FaChild } from 'react-icons/fa';
import { useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import { mockCares } from '../../utils/global-default-values';
import ProgressCare from '../ProgressCare/ProgressCare';
import './ProgressCareList.css';

interface ProgressCareListProps { }

export const ProgressCareList: FC<ProgressCareListProps> = () => {
  return (
    <div className="ProgressCareList" data-testid="ProgressCareList">
      <div className='ProgressCares'>
        <div className='child-name'><FaChild /> John Peter</div>
        {
          mockCares.map((care, index) => <ProgressCare care={care} key={index} />)
        }
      </div>
      <div className='ProgressCares'>
        <div className='child-name'><FaChild /> Marie Wilnie</div>
        {
          mockCares.map((care, index) => <ProgressCare care={care} key={index} />)
        }
      </div>

    </div>
  );
}

export default ProgressCareList;
