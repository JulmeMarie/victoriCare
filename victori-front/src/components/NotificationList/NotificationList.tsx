import React, { FC, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import { updateLang } from '../../redux/reducers/app-reducer';
import './NotificationList.css';
import { LANGUAGES } from '../../utils/Constants';
import { FaBell } from 'react-icons/fa';


interface NotificationListProps { }

const NotificationList: FC<NotificationListProps> = () => {
  const dispatch = useDispatch();
  const notification = useSelector((state: AppState) => state.notificationReducer);
  const unSeen = notification.unSeen;
  const [isOpen, setIsOpen] = useState(false);
  const lang = i18n.langStr;

  const handleClick = () => {
    i18n.setLang(lang).then(() => {
      dispatch(updateLang(lang));
    });
  }

  return (
    <div className="NotificationList" data-testid="NotificationList" onClick={() => { setIsOpen(!isOpen) }}>
      <div className='flex-row'>
        <div className='em-icon'><FaBell className='item-icon' /></div>
        <div className='menu-text'>{i18n.t("menu.notifications")}</div>
      </div>
      {
        isOpen && <ul>
          {
            unSeen.map((notif, index) =>
              <li className='flex-row' key={index}> <div className='em-icon'>
              </div> <div className='menu-text'> {notif.title}</div></li>
            )
          }
        </ul>
      }
    </div>
  );
}

export default NotificationList;
