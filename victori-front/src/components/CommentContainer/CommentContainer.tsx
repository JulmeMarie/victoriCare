import { set } from 'immer/dist/internal';
import React, { FC, useEffect, useState } from 'react';
import { FaBaby, FaBirthdayCake, FaChild, FaAngleLeft, FaAngleRight, FaQuoteLeft, FaPauseCircle } from 'react-icons/fa';
import './CommentContainer.css';
import { DIRECTIONS } from '../../utils/Constants'
import { CommentService } from '../../services/CommentService';
import { useDispatch, useSelector } from 'react-redux';
import { AppState } from '../../redux/store';
import { EDirectionType } from '../../utils/global-types';
interface CommentContainerProps { }

const gap = 100 / 6;
const minX = 0;
const maxX = 5 * gap;
const delay = 10000;

const commentService = new CommentService();

const CommentContainer: FC<CommentContainerProps> = () => {
  const [translateX, setTranslateX] = useState(0);
  const [stop, setStop] = useState(false);
  const comments = useSelector((state: AppState) => state.commonReducer.comments);
  const dispatch = useDispatch();

  if (!comments.isLoading && !comments.isLoaded) {
    commentService.setDispatch(dispatch);
    commentService.initComment();
  }

  if (!stop) {
    setTimeout(() => {
      goTo(DIRECTIONS.RIGHT);
    }, delay);
  }

  const handleClick = (direction: EDirectionType) => {
    setStop(true);
    goTo(direction);
  }

  const goTo = (direction: EDirectionType) => {
    let newTranslateX = Math.abs(translateX);
    if (direction === DIRECTIONS.LEFT) { //LEFT
      newTranslateX = Math.trunc(newTranslateX) === minX ? maxX : newTranslateX - gap;
    }
    else {
      newTranslateX = Math.trunc(newTranslateX) === Math.trunc(maxX) ? minX : newTranslateX + gap;
    }
    newTranslateX = -1 * newTranslateX;
    setTranslateX(Number(newTranslateX.toFixed(2)));
  }

  return (
    <div className="CommentContainer" data-testid="CommentContainer">
      <h1 className='title Second-content__title'>COMMENTAIRES DES PARENTS </h1>
      <div className='direction prev' onClick={() => handleClick(DIRECTIONS.LEFT)}><FaAngleLeft /></div>
      {comments.isLoading && <div>Commentaires en cours de chargement</div>}
      {comments.isError && <div> {comments.error}</div>}
      <section className='comments-list' style={{
        transform: "translateX(" + translateX + "%)"
      }}>
        {
          comments.data && comments.data.list.map((comment: any, index: number) =>
            <div className='comment-item' key={index}>
              <div className='row comment-item__photo'>
                <img src={process.env.PUBLIC_URL + "img/parents/" + comment.img} alt="parent" onClick={() => setStop(true)} />
              </div>
              <div className='row comment-item__username'>{comment.username}</div>
              <div className='row comment-item__role'>{comment.role}</div>
              <div className='row comment-item__message'>{comment.message}</div>
              <div className='row comment-item_quote'><FaQuoteLeft /></div>
            </div>
          )
        }
      </section>
      <div className='direction next' onClick={() => handleClick(DIRECTIONS.RIGHT)}><FaAngleRight /></div>
      {stop && <div className='comment-item_play' onClick={() => setStop(false)}><FaPauseCircle /> <div className="comment-item-play__text">pause </div></div>}
    </div>
  );
}

export default CommentContainer;

