import React, { FC } from 'react';
import './Tooltip.css';

interface TooltipProps { }

const Tooltip: FC<TooltipProps> = () => (
  <div className="Tooltip" data-testid="Tooltip">
    <div className="tooltip-text">
      <p>this is a simple bubble effect   <br />width CSS pseudo element </p>
    </div>
  </div>
);

export default Tooltip;
