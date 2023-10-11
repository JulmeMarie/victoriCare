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
  const lang = i18n.langStr;
  const langArr = ["en", "fr", "ht", "es"];

  const handleLangChange = (lang: string) => {
    i18n.setLang(lang).then(() => {
      dispatch(updateLang(lang));
    });
  }

  return (
    <div className="Language" data-testid="Language" onClick={() => { setIsOpen(!isOpen) }}>
      <div> <i className={"em em-" + lang}></i> {i18n.t("lang." + lang)}</div>
      {
        isOpen && <ul>
          {
            langArr.map((key, index) =>
              key !== lang && <li key={index} onClick={() => handleLangChange(key)}> <i className={"em em-" + key}></i> {i18n.t("lang." + key)}</li>
            )
          }
        </ul>
      }
    </div>
  );
}

export default Language;
