import React, { FC } from "react";
import "./Comment.css";
import { IComment } from "../../utils/global-interfaces";
import { FaQuoteLeft } from "react-icons/fa";

interface CommentProps {
  comment: IComment;
}

const Comment: FC<CommentProps> = ({ comment }) => {
  return (
    <div className="Comment" data-testid="Comment">
      <figure>
        <img
          src={process.env.PUBLIC_URL + "img/parents/" + comment.image}
          alt="parent"
        />
        <figcaption>
          <div className="comment-username">{comment.username}</div>
          <div className="comment-role">{comment.role}</div>
          <div className="comment-message">{comment.message}</div>
          <div className="comment-quote">
            <FaQuoteLeft />
          </div>
        </figcaption>
      </figure>
    </div>
  );
};

export default Comment;
