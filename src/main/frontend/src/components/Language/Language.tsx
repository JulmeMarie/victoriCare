import React, { FC, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import i18n from '../../utils/languages/I18N';
import { updateLang } from '../../redux/reducer';
import './Language.css';

interface LanguageProps { }

const Language: FC<LanguageProps> = () => {
  const dispatch = useDispatch();
  const [isOpen, setIsOpen] = useState(false);
  const lang = useSelector((state: RootState) => state.app_state.user.profil.lang);

  const onLangChange = (lang: string) => {
    i18n.setLang(lang).then(() => {
      dispatch(updateLang(lang));
      localStorage.setItem("babicare-lang", lang);
    });
  }

  return (
    <div className="Language" data-testid="Language" onClick={() => { setIsOpen(!isOpen) }}>
      <div>Langue : {lang}</div>
      {
        isOpen && <ul>
          <li onClick={() => onLangChange('fr')}>fr</li>
          <li onClick={() => onLangChange('en')}>en</li>
          <li onClick={() => onLangChange('ht')}>ht</li>
        </ul>
      }
    </div>
  );
}

export default Language;
