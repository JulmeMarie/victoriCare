import React, { FC, ReactElement, useState } from 'react';
import { useDispatch } from 'react-redux';
import './DropDown.css';

interface DropDownProps {
  items: Array<ReactElement>,
  title: ReactElement,
  footer?: ReactElement
}

const DropDown: FC<DropDownProps> = ({ title, items, footer }) => {
  const dispatch = useDispatch();
  const [isOpen, setIsOpen] = useState(false);

  /*
  const handleLangChange = (lang: EDropDownType) => {
    i18n.setLang(lang).then(() => {
      dispatch(updateLang(lang));
    });
  }*/

  return (
    <div className='DropDown width-100 flex-column' data-testid="DropDown">
      <div className='DropDown-header width-100 flex-row' onClick={() => { setIsOpen(!isOpen) }}>{title}</div>
      {isOpen &&
        <div className='DropDown-body width-100 flex-column'>
          {items.map((item, index) => <div key={index} className='DropDown-item width-100 flex-row'>{item}</div>)}
        </div>
      }
      {footer && <div className='DropDown-footer width-100 flex-row' onClick={() => { setIsOpen(!isOpen) }}>{footer}</div>}
    </div>
  );
}

export default DropDown;
