import React, { FC, useState } from "react";
import {
    FaAngleLeft,
    FaAngleRight
} from "react-icons/fa";
import "./CommentList.css";
import { ALERTS, DIRECTIONS } from "../../utils/Constants";
import { EDirectionType } from "../../utils/global-types";
import useComments from "../../customHooks/useComments";
import Comment from "../Comment/Comment";
import { IComment } from "../../utils/global-interfaces";
import Alert from "../Alert/Alert";
interface CommentListProps { }

const gap = 100 / 6;
const minX = 0;
const maxX = 5 * gap;
const delay = 10000;

const CommentList: FC<CommentListProps> = () => {
    const [translateX, setTranslateX] = useState(0);
    const [stop, setStop] = useState(false);
    const commentResult = useComments();

    if (!stop) {
        setTimeout(() => {
            goTo(DIRECTIONS.RIGHT);
        }, delay);
    }

    const handleClick = (direction: EDirectionType) => {
        setStop(true);
        goTo(direction);
    };

    const goTo = (direction: EDirectionType) => {
        let newTranslateX = Math.abs(translateX);
        if (direction === DIRECTIONS.LEFT) {
            newTranslateX =
                Math.trunc(newTranslateX) === minX ? maxX : newTranslateX - gap;
        } else {
            newTranslateX =
                Math.trunc(newTranslateX) === Math.trunc(maxX)
                    ? minX
                    : newTranslateX + gap;
        }
        newTranslateX = -1 * newTranslateX;
        setTranslateX(Number(newTranslateX.toFixed(2)));
    };

    const leftArrow = () => {
        return (
            <div
                className="direction prev"
                onClick={() => handleClick(DIRECTIONS.LEFT)}
            >
                <FaAngleLeft />
            </div>
        );
    }

    const rightArrow = () => {
        return (
            <div
                className="direction next"
                onClick={() => handleClick(DIRECTIONS.RIGHT)}
            >
                <FaAngleRight />
            </div>
        );
    }

    const getStatus = () => {
        return (
            <>
                {
                    commentResult.isLoading &&
                    <Alert type={ALERTS.INFO} message="Commentaire en cours de chargement..." showIcon={true} />
                }
                {commentResult.isError &&
                    <Alert type={ALERTS.ERROR} message={commentResult.error} showIcon={true} />
                }
            </>
        )
    }

    return (
        <div className="CommentList" data-testid="CommentList">
            <h1 className="title main-title">COMMENTAIRES DES PARENTS </h1>
            {leftArrow()}
            {getStatus()}
            <div
                className="comments-wrapper"
                style={{
                    transform: "translateX(" + translateX + "%)",
                }}
                onMouseOver={() => setStop(true)}
                onMouseOut={() => setStop(false)}
            >
                {(commentResult.data || []).map((comment: IComment, index: number) =>
                    <Comment key={index} comment={comment} />
                )}
            </div>
            {rightArrow()}
        </div>
    );
};

export default CommentList;
