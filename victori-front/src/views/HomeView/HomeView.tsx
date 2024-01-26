import React, { FC } from 'react';
import { useSelector } from 'react-redux';
import { Navigate, useOutlet } from 'react-router-dom';
import CareList from '../../components/CareList/CareList';
import Sidebar from '../../components/Sidebar/Sidebar';
import { AppState } from '../../redux/store';
import { CONTENTSNAME } from '../../utils/Constants';
import './HomeView.css';

interface HomeViewProps { }

const HomeView: FC<HomeViewProps> = () => {
  const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
  const outlet = useOutlet();

  if (!logIn) {
    console.log("redirect to home");
    return <Navigate to="/" />
  }

  return (
    <main className="HomeView main-page" data-testid="HomeView">
      <div className='First-content__container'>
        <article className='left-main-content'>
          {outlet || <CareList />}
        </article>
        <Sidebar contentNameProp={CONTENTSNAME.PROGRESSCARES} />
      </div>
    </main>
  );
}

export default HomeView;
