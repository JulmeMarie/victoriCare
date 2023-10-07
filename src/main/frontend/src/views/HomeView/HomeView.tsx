import React, { FC } from 'react';
import styles from './HomeView.module.css';

interface HomeViewProps {}

const HomeView: FC<HomeViewProps> = () => (
  <div className={styles.HomeView} data-testid="HomeView">
    HomeView Component
  </div>
);

export default HomeView;
