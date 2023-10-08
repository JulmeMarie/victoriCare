import React, { FC } from 'react';
import './IndexView.css';

interface IndexViewProps { }

const IndexView: FC<IndexViewProps> = () => (
  <div className='IndexView' data-testid="IndexView">
    IndexView Component
  </div>
);

export default IndexView;
