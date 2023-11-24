import React, { FC, useEffect, useRef, useState } from 'react';
import { FaGripVertical } from 'react-icons/fa';
import { DIRECTIONS } from '../../../utils/Constants';
import { IColorConfig } from '../../../utils/datatable-interfaces';
import { KeyPair } from '../../../utils/global-interfaces';
import { isEqual } from '../../../utils/global-util';
import './DragableBox.css';

export interface IMoveConfig {
  direction: string,
  callBack: (key: string) => void,
}
interface DragableBoxProps {
  title: string,
  items: Array<KeyPair>,
  moveConfig: IMoveConfig | undefined,
  onDragEnd?: (items: Array<KeyPair>) => void
}

interface IClient {
  X: number,
  Y: number
}

const DragableBox: FC<DragableBoxProps> = ({ title, items, moveConfig, onDragEnd }) => {
  const ULRef = useRef<HTMLUListElement | null>(null);

  var startClient = {
    X: 0,
    Y: 0
  } as IClient

  var prevClient = {
    X: 0,
    Y: 0
  } as IClient

  const [localItems, setLocalItems] = useState<Array<KeyPair> | null>();

  useEffect(() => {
    setLocalItems(items);
  }, [items]);

  const insertAfter = (newNode: HTMLLIElement, existingNode: HTMLLIElement) => {
    existingNode.parentNode?.insertBefore(newNode, existingNode.nextSibling);
  }

  const handleDragOver = (event: React.MouseEvent<HTMLLIElement>) => {
    event.preventDefault();

    const eventClient = {
      X: event.clientX,
      Y: event.clientY
    } as IClient

    if (!isEqual(eventClient, prevClient)) {
      console.log("handleDragOver")
      prevClient = eventClient;

      const UL = ULRef.current;
      const draggingLI = UL?.querySelector('.dragging') as HTMLLIElement;

      if (UL && draggingLI) {
        const LISibblings = [...UL.querySelectorAll('.DragableBox-item:not(.dragging)')] as Array<HTMLLIElement>;
        const rec = UL.getBoundingClientRect();
        const nextLISibbling = LISibblings.find(LISibbling => {
          const mouseYPosition = (event.clientY - (rec?.top || 0));
          const isMouseInMiddle = mouseYPosition >= LISibbling.offsetTop && mouseYPosition < (LISibbling.offsetTop + LISibbling.offsetHeight / 2);
          return isMouseInMiddle;
        });

        if (nextLISibbling) {
          const isDown = event.clientY - startClient.Y > 0;
          isDown ?
            insertAfter(draggingLI, nextLISibbling) :
            UL.insertBefore(draggingLI, nextLISibbling);
        }
      }
    }
  }

  const handleDragEnd = (event: React.MouseEvent<HTMLLIElement>, key: string) => {
    console.log("onDragEnd")
    startClient.Y = 0;
    const LI = event.target as HTMLLIElement;
    LI.classList.remove("dragging");

    const UL = ULRef.current;
    const LIs = UL ? [...UL.querySelectorAll('.DragableBox-item')] : [] as Array<HTMLElement>;
    const sortedItems = LIs.map(LI => (localItems || []).find(item => item.key === LI.getAttribute("data-key"))) as Array<KeyPair>;
    setLocalItems(null);
    onDragEnd ? onDragEnd(sortedItems) : setLocalItems(sortedItems);

    if (moveConfig && moveConfig !== undefined) {
      const distance = event.clientX - startClient.X;

      const isCorrectDirection = (moveConfig.direction === DIRECTIONS.LEFT && distance < 0) ||
        (moveConfig.direction === DIRECTIONS.RIGHT && distance > 0);

      const isIntentional = Math.abs(distance) >= (LI.offsetWidth / 2);
      (isCorrectDirection && isIntentional) && moveConfig.callBack(key);
    }
  }

  const handleDragStart = (event: React.MouseEvent<HTMLLIElement>) => {
    startClient.Y = event.clientY;
    startClient.X = event.clientX;

    const LI = event.target as HTMLLIElement;
    setTimeout(() => LI.classList.add("dragging"), 0);
  }

  return (
    <div className="DragableBox" data-testid="DragableBox">
      <h4 className='DragableBox-title'>{title}</h4>

      <ul className='DragableBox-list custom-scroll-bar xs vertical-scroll-bar' ref={ULRef}>
        {(localItems || []).map((item, index) =>
          <li key={index} className="DragableBox-item"
            data-testid="DragableBox-item"
            data-key={item.key}
            draggable={true}
            onDragStart={(event) => handleDragStart(event)}
            onDragEnd={(event) => handleDragEnd(event, item.key)}
            onDragOver={(event) => handleDragOver(event)}
          >
            <div className='DragableBox-item__value'>{item.value}</div>
            <FaGripVertical className='DragableBox-item__icon' />
          </li>)}
      </ul>
    </div>
  );
}

export default DragableBox;