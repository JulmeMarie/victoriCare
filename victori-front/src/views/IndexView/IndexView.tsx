import { FC } from 'react';
import { Navigate } from 'react-router-dom';
import CommentList from '../../components/CommentList/CommentList';
import Sidebar from '../../components/Sidebar/Sidebar';
import { useSelector } from 'react-redux';
import './IndexView.css';
import { AppState } from '../../redux/store';
import { EContentNameType } from '../../utils/global-types';
import HelpList from '../../components/HelpList/HelpList';
import ServiceList from '../../components/ServiceList/ServiceList';

interface IndexViewProps {
  formName: EContentNameType
}

const IndexView: FC<IndexViewProps> = ({ formName }) => {
  const logIn = useSelector((state: AppState) => state.commonReducer.logIn);

  if (logIn) {
    console.log("redirect to home");
    return <Navigate to="/home" />
  }


  return (
    <main className='IndexView' data-testid="IndexView">
      <div className='IndexView-row flex-row'>
        <HelpList />
        <Sidebar contentNameProp={formName} />
      </div>
      <ServiceList />
      <CommentList />
    </main>
  );
}

export default IndexView;