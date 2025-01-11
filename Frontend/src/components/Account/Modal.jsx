// import { useState, useEffect } from 'react'
import { createPortal } from 'react-dom';
import { FaXmark} from 'react-icons/fa6';

const Modal = ({ children, isOpen, onClose, isLogin}) => {

  if (!isOpen) return null;

  return createPortal(
    <div className="fixed top-0 left-0 w-full h-full bg-black bg-opacity-70 flex justify-center items-center z-50" 
    onClick={onClose}> 
      <div className = {`relative bg-off-white rounded-3xl w-[25rem] transition-all duration-300 ease-in-out
       ${isLogin ? 'h-[27.5rem]' : 'h-[32.5rem]'} shadow-md animate-fadeIn flex flex-col items-center space-y-4 p-5`}
       onClick={(e) => e.stopPropagation()}>
          <span onClick = {onClose} className="absolute top-4 right-4 w-9 h-9 text-xl flex items-center justify-center bg-slate-800 rounded-3xl cursor-pointer "> <FaXmark className='text-white'/></span>
            {children}            
      </div>
    </div>,
    document.body
  );
};

export default Modal;



// ChatGPT Adds 'ESC' to close Modal
// 
// useEffect(() => {
//   const handleKeyDown = (e) => {
//     if (e.key === "Escape" && isOpen) {
//       onClose();
//     }
//   };
//   window.addEventListener("keydown", handleKeyDown);
//   return () => window.removeEventListener("keydown", handleKeyDown);
// }, [isOpen, onClose]);