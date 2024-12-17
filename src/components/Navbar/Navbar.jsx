import { useState } from 'react'
// import { NavLink } from 'react-router'
/* eslint-disable react/prop-types */
// eslint-disable-next-line no-unused-vars
import { createPortal } from 'react-dom'
import Login from '../Account/Login.jsx'
import Register from '../Account/Register.jsx'
//import PopUp from '../Account/PopUp.jsx'
import Modal from '../Account/Modal.jsx'

import './Navbar.css'
const Navbar = ({checkLogin, sendRegister}) => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);

  const rootElem = document.querySelector('#root');

  return (
    <>
        <header className=''> 
            {/* <NavLink> */}
              <h2 className="text-3xl text-white font-semibold select-none">TrendSight</h2> 
              {/* Prev /logo */}
            {/* </NavLink> */}
            <nav className="navigation">
                <a href="#" id = "Papers">Papers</a>
                <button onClick={() => {
                  setShowPopUp(true); 
                  //setShowLogin(true)
                  }} 
                  className="login-popup">
                <button onClick={() => {
                  setShowPopUp(true); 
                  //setShowLogin(true)
                  }} 
                  className="login-popup">
                  Login              
                </button>
            </nav>
        </header>

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