import React, { FC } from 'react';
import './EventView.css';

interface EventViewProps {}

const EventView: FC<EventViewProps> = () => (
  <div className="EventView" data-testid="EventView">
    EventView Component
  </div>
);

export default EventView;
