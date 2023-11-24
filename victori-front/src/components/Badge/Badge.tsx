import React, { FC } from 'react';
import './Badge.css';

interface BadgeProps {}

const Badge: FC<BadgeProps> = () => (
  <div className="Badge" data-testid="Badge">
    Badge Component
  </div>
);

export default Badge;
