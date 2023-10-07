import React, { FC } from 'react';
import styles from './IndexView.module.css';

interface IndexViewProps {}

const IndexView: FC<IndexViewProps> = () => (
  <div className={styles.IndexView} data-testid="IndexView">
    IndexView Component
  </div>
);

export default IndexView;
