import React, { FC, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import { updateLang } from '../../redux/reducers/app-reducer';
import './Language.css';
import { LANGUAGES } from '../../utils/Constants';
import { ELanguageType } from '../../utils/global-types';


interface LanguageProps { }

const Language: FC<LanguageProps> = () => {
  const dispatch = useDispatch();
  const [isOpen, setIsOpen] = useState(false);
  const lang = i18n.langStr;

  const handleLangChange = (lang: ELanguageType) => {
    i18n.setLang(lang).then(() => {
      dispatch(updateLang(lang));
    });
  }

  return (
    <div className="Language" data-testid="Language" onClick={() => { setIsOpen(!isOpen) }}>
      <div className='flex-row'>
        <div className='em-icon'><i className={"em em-" + lang}></i></div>
        <div className='menu-text'>{i18n.t("lang." + lang)}</div>
      </div>
      {
        isOpen && <ul>
          {
            Object.values(LANGUAGES).map((value, index) =>
              value !== lang && <li className='flex-row' key={index} onClick={() => handleLangChange(value)}> <div className='em-icon'><i className={"em em-" + value}></i></div> <div className='menu-text'> {i18n.t("lang." + value)}</div></li>
            )
          }
        </ul>
      }
    </div>
  );
}

export default Language;
