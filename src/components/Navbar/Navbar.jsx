import { useState } from 'react'
// import { NavLink } from 'react-router'

import Login from '../Account/Login.jsx'
import Register from '../Account/Register.jsx'

import './Navbar.css'
const Navbar = ({checkLogin, sendRegister}) => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);


  return (
    <>
        <header> 
            {/* <NavLink> */}
              <h2 className="logo" to="/">TrendSight</h2>
            {/* </NavLink> */}
            <nav className="navigation">
                <a href="#" id = "Papers">Papers</a>
                <button onClick={() => {
                  setShowPopUp(true); 
                  //setShowLogin(true)
                  }} 
                  className="login-popup">
                  Login              
                </button>
            </nav>
        </header>

        <div className = {`wrapper ${showPopUp ? 'active-popup' : ''} ${isLogin ? '' : 'active'}`}>
          {isLogin ? <Login checkLogin = {checkLogin} setShowLogin = {setShowLogin} setClosePopup = {setShowPopUp}/> : <Register sendRegister = {sendRegister} setShowLogin = {setShowLogin} setClosePopup={setShowPopUp}/>}
        </div>
    </>

  )
}

export default Navbar