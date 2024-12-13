import { useState } from 'react'
import { NavLink } from 'react-router'

import Login from '../Account/Login.jsx'
import Register from '../Account/Register.jsx'

import './Navbar.css'
const Navbar = () => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);

  const checkLogin = async(credentials) =>{
    const res = await fetch("http://localhost:5000/accounts", {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(credentials),
    });
    console.log("HTTP Login POST Req finished");
    return;
  }

  const sendRegister = () => {

  }

  return (
    <>
        <header> 
            {/* <NavLink> */}
              <h2 className="logo" to="/">TrendSight</h2>
            {/* </NavLink> */}
            <nav className="navigation">
                <a href="#" id = "Papers">Papers</a>
                <button onClick={() => {setShowPopUp(true); setShowLogin(true)}} className="login-popup">
                  Login              
                </button>
            </nav>
        </header>

        <div className = {`wrapper ${showPopUp ? 'active-popup' : ''} ${isLogin ? '' : 'active'}`}>
          {isLogin ? <Login checkLogin = {checkLogin} setShowLogin = {setShowLogin} setClosePopup = {setShowPopUp}/> : <Register setShowLogin = {setShowLogin} setClosePopup={setShowPopUp}/>}
        </div>
    </>

  )
}

export default Navbar