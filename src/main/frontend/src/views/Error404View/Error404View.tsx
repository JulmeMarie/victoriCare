import React, { FC } from 'react';
import styles from './Error404View.module.css';

interface Error404ViewProps { }

const Error404View: FC<Error404ViewProps> = () => (
  <div className={styles.Error404View} data-testid="Error404View">
    Error404View Component
  </div>
);

export default Error404View;
