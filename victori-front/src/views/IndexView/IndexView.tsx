import { FC } from 'react';
import { Navigate } from 'react-router-dom';
import CommentContainer from '../../components/CommentContainer/CommentContainer';
import ContactForm from '../../components/Forms/ContactForm/ContactForm';
import ServiceCard from '../../components/ServiceCard/ServiceCard';
import Sidebar from '../../components/Sidebar/Sidebar';
import { useSelector } from 'react-redux';
import './IndexView.css';
import { AppState } from '../../redux/store';
import { EContentNameType } from '../../utils/global-types';
import I18N from '../../utils/languages/I18N';

interface IndexViewProps {
  formName: EContentNameType
}

export interface IHelp {
  text: string,
  image: string
}

export interface IService {
  title: string,
  paragraphs: Array<string>,
  icon: string
}

export interface IVictoriHelp {
  title: string,
  contents: Array<IHelp>
}

export interface IVictoriService {
  title: string,
  contents: Array<IService>
}

const IndexView: FC<IndexViewProps> = ({ formName }) => {
  const logIn = useSelector((state: AppState) => state.commonReducer.logIn);
  const victoriHelp = I18N.t("victoriHelp") as IVictoriHelp;
  const victoriService = I18N.t("victoriService") as IVictoriService;

  if (logIn) {
    console.log("redirect to home");
    return <Navigate to="/home" />
  }


  return (
    <main className='IndexView main-page' data-testid="IndexView">
      <div className='First-content__container'>
        {
          victoriHelp &&
          <article className='left-main-content' id="about-us">
            <h1 className='title Introduction-sentence__title'>{victoriHelp.title} </h1>
            {victoriHelp.contents.map((content: IHelp, index: number) =>
              <section className='Introduction-sentence__description' key={index}>
                <p>{content.text}</p>
                <figure className='Introduction-sentence__image'>
                  <img src={process.env.PUBLIC_URL + content.image} alt={content.image} />
                </figure>
              </section>
            )}
          </article>
        }
        <Sidebar contentNameProp={formName} />
      </div>
      {victoriService &&
        <article className='Second-content__container'>
          <h1 className='title Second-content__title'>{victoriService.title} </h1>
          <div className='Second-content__cards'>
            {
              victoriService.contents.map((serv: IService, index: number) =>
                <ServiceCard key={index} service={serv} />)
            }
          </div>
        </article>}
      <article className='Third-content__container'>
        <CommentContainer />
      </article>
      <article className='Forth-content__container' id="contact-form">
        <ContactForm />
      </article>
    </main>
  );
}

export default IndexView;