import React, { FC, ReactElement, useState } from 'react';
import './Modal.css';
import { FaTimes } from 'react-icons/fa';

interface ModalProps {
    children: ReactElement,
    onClose?: () => void;
}

const Modal: FC<ModalProps> = ({ children, onClose }) => {
    const [close, setClose] = useState(false);

    function handleClose(event: React.MouseEvent<HTMLDivElement, MouseEvent>): void {
        if (!onClose) return;
        event.stopPropagation();
        setClose(true);
        setTimeout(() => { onClose() }, 500);
    }

    return (
        <div className="Modal closable-target" data-testid="Modal">
            <div className={`Modal-content ${close && 'close'}`}>
                <div className='Modal-close closable-target' onClick={(event) => handleClose(event)}>
                    <FaTimes />
                </div>
                {children}
            </div>
        </div>
    );
}

export default Modal;
