import React, { FC } from 'react';
import './Notification.css';

interface NotificationProps {}

const Notification: FC<NotificationProps> = () => (
  <div className="Notification" data-testid="Notification">
    Notification Component
  </div>
);

export default Notification;
