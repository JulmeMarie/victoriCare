import { set } from 'immer/dist/internal';
import React, { FC, useEffect, useState } from 'react';
import { FaBaby, FaBirthdayCake, FaChild, FaAngleLeft, FaAngleRight, FaQuoteLeft, FaPauseCircle } from 'react-icons/fa';
import './CommentContainer.css';
import { Direction } from '../../utils/Enums'
interface CommentContainerProps { }

const comments = [
  {
    username: "VictoriCare",
    role: "Administrateur",
    message: "Bonjour, nous sommes heureux de partager avec vous les commentaires de nos utilisateurs. Merci",
    img: "0.png"
  },
  {
    username: "Samano CASTRE",
    role: "Parent",
    message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
    img: "1.jpg"
  },
  {
    username: "JULME MARIE WILNIE",
    role: "Parent",
    message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
    img: "2.jpg"
  },
  {
    username: "JULME MARIE WILNIQUE",
    role: "Parent",
    message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
    img: "3.jpg"
  },
  {
    username: "DEMOSTHERNE WOODELINE",
    role: "Parent",
    message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
    img: "4.jpg"
  },
  {
    username: "DEMOSTHERNE RAYMOND",
    role: "Baby Siter",
    message: "Il existe de nombreuses variantes de passages du Lorem Ipsum disponibles, mais la majorité ont subi des altérations sous une forme ou une autre, par injection d'humour ou par des mots aléatoires qui ne semblent même pas légèrement crédibles. Si vous comptez utiliser un passage du Lorem Ipsum, vous devez vous assurer qu'il n'y a rien d'embarrassant caché au milieu du texte. Tous les générateurs Lorem Ipsum sur Internet ont tendance à répéter des valeurs prédéfinies.",
    img: "5.jpg"
  },
];

const gap = 100 / 6;
const minX = 0;
const maxX = 5 * gap;
const delay = 5000;

const CommentContainer: FC<CommentContainerProps> = () => {
  const [translateX, setTranslateX] = useState(0);
  const [stop, setStop] = useState(false);

  if (!stop) {
    setTimeout(() => {
      goTo(Direction.RIGHT);
    }, delay);
  }

  const handleCick = (direction: number) => {
    setStop(true);
    goTo(direction);
  }

  const goTo = (direction: number) => {
    let newTranslateX = Math.abs(translateX);
    if (direction === Direction.LEFT) { //LEFT
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
      <div className='direction prev' onClick={() => handleCick(Direction.LEFT)}><FaAngleLeft /></div>
      <section className='comments-list' style={{
        transform: "translateX(" + translateX + "%)"
      }}>
        {
          comments && comments.map((comment, index) =>
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
      <div className='direction next' onClick={() => handleCick(Direction.RIGHT)}><FaAngleRight /></div>
      {stop && <div className='comment-item_play' onClick={() => setStop(false)}><FaPauseCircle /> <div className="comment-item-play__text">pause </div></div>}
    </div>
  );
}

export default CommentContainer;
