import { FC } from 'react';
import { useOutlet } from 'react-router-dom';
import CommentContainer from '../../components/CommentContainer/CommentContainer';
import ContactForm from '../../components/Forms/ContactForm/ContactForm';
import ServiceCard from '../../components/ServiceCard/ServiceCard';
import Sidebar from '../../components/Sidebar/Sidebar';
import { useDispatch, useSelector } from 'react-redux';
import './IndexView.css';
import { DocumentationService } from '../../services/DocumentationService';
import { AppState } from '../../redux/store';

interface IndexViewProps { }
export interface Service {
  title: string,
  paragraphs: string[],
  icon: string
}

const docService = DocumentationService.create();

const IndexView: FC<IndexViewProps> = () => {
  const documentation = useSelector((state: AppState) => state.common_state.documentation);
  const dispatch = useDispatch();
  const outlet = useOutlet();

  if (!documentation.isLoading && !documentation.isLoaded) {
    docService.setDispatch(dispatch);
    docService.initDoc();
  }

  return (
    <main className='IndexView' data-testid="IndexView">
      {documentation.isLoading && <div>On est en loading...</div>}
      {documentation.isError && <div>Il y une erreur : {documentation.error}</div>}
      <div className='First-content__container'>
        {
          outlet ||
          <>{
            documentation.data && <article className='Introduction-sentence__box'>
              <h1 className='title Introduction-sentence__title'>COMMENT VICTORICARE VOUS AIDE ? </h1>
              {documentation.data.catchSetences.map((sentence: any, index: number) =>
                <section className='Introduction-sentence__description' key={index}>
                  <p>{sentence.description}</p>
                  <figure className='Introduction-sentence__image'>
                    <img src={process.env.PUBLIC_URL + sentence.img} alt={sentence.img} />
                  </figure>
                </section>
              )}
            </article>
          }
          </>
        }
        <Sidebar />
      </div>
      {documentation.data &&
        <article className='Second-content__container'>
          <h1 className='title Second-content__title'>CE QUE NOUS OFFRONS </h1>
          <div className='Second-content__cards'>
            {documentation.data.servicesTab.map((serv: any, index: number) => <ServiceCard key={index} service={serv} />)}
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