import { useState } from 'react'
import Login from '../PopUp/Login.jsx'
import Register from '../PopUp/Register.jsx'

import './Navbar.css'
const Navbar = () => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);

  return (
    <>
        <header> 
            <h2 className="logo">TrendSight</h2>
            <nav className="navigation">
                <a href="#" id = "homePg">Home</a>
                <a href="#" id = "aboutPg">About</a>
                <button onClick={() => {setShowPopUp(true); setShowLogin(true)}} className="login-popup">
                  Login              
                </button>
            </nav>
        </header>

        <div className = {`wrapper ${showPopUp ? 'active-popup' : ''} ${isLogin ? '' : 'active'}`}>
          {isLogin ? <Login setShowLogin = {setShowLogin} setClosePopup = {setShowPopUp}/> : <Register setShowLogin = {setShowLogin} setClosePopup={setShowPopUp}/>}
        </div>
    </>

  )
}

export default Navbar