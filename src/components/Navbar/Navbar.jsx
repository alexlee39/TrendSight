/* eslint-disable react/prop-types */
import { useState } from 'react'
import { Link } from 'react-router'
import Login from '../Account/Login.jsx'
import Register from '../Account/Register.jsx'
import Modal from '../Account/Modal.jsx'

const Navbar = ({checkLogin, sendRegister}) => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);

  return (
    <>  
        
        <nav className='fixed w-full top-0 left-0 px-24 py-5 bg-zinc-500 flex justify-between items-center z-50'> 
          <Link href="/" className="text-4xl text-white font-semibold select-none">TrendSight</Link> 
            <div className="navigation">
              <Link to ="/" className="relative text-lg text-white font-medium my-2 mx-6 group">
                Papers
                  {/* Bar that is displayed once hovered */}
                  <span
                    className="absolute -bottom-2 right-0 w-0 h-[3px] bg-white transition-all rounded-md duration-[400ms] group-hover:w-full group-hover:left-0"
                  >
                  </span>
                </Link>

                <button onClick={() => {
                  setShowPopUp(true); 
                  }} 
                  className="login-popup text-lg text-white font-medium py-2 px-10 mx-4 bg-transparent border-2 border-solid border-white outline-none rounded-md hover:text-black hover:bg-white hover:duration-500">
                  Login              
                </button>
            </div>
        </nav>

        <Modal isOpen={showPopUp} onClose={() => setShowPopUp(false)} isLogin = {isLogin}>
        {isLogin ? (
          <Login
            checkLogin={checkLogin}
            setShowLogin={setShowLogin}
            setClosePopup={setShowPopUp}
          />
        ) : (
          <Register
            sendRegister={sendRegister}
            setShowLogin={setShowLogin}
            setClosePopup={setShowPopUp}
          />
        )}
      </Modal>
        {/* {showPopUp && createPortal(
          <PopUp showPopUp={ showPopUp } isLogin={ isLogin } setShowPopUp={ setShowPopUp}
           setShowLogin={ setShowLogin} checkLogin={ checkLogin } sendRegister={ sendRegister}/>,
           document.getElementById('root') 
        )} */}


    </>

  )
}

export default Navbar