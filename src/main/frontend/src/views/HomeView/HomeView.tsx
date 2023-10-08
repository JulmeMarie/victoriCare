import React, { FC } from 'react';
import './HomeView.css';

interface HomeViewProps { }

const HomeView: FC<HomeViewProps> = () => (
  <div className='HomeView' data-testid="HomeView">
    HomeView Component
  </div>
);

export default HomeView;
