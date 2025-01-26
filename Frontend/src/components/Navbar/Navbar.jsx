/* eslint-disable react/prop-types */
import { useState } from 'react'
import { Link } from 'react-router'
import Login from '../Account/Login.jsx'
import Register from '../Account/Register.jsx'
import Modal from '../Account/Modal.jsx'
import Navlink from './Navlink.jsx'
import { Button } from '../ui/button.jsx'
import { useNavigate } from 'react-router'

const Navbar = ({checkLogin, sendRegister, role, logout}) => {
  const [showPopUp, setShowPopUp] = useState(false);
  const [isLogin, setShowLogin] = useState(true);
  // const [role, setrole] = useState(role);
  // console.log(role);
  let navigate = useNavigate();


  console.log(role);
  // Normal User Portal to only see Papers
  if(role === null || role === ''){
    return (
      <>  
          <nav className='fixed w-full top-0 left-0 px-24 py-5 bg-zinc-500 flex justify-between items-center z-50'> 
            <Link href="/" className="text-4xl text-white font-semibold select-none">TrendSight</Link> 
              <div className="navigation">
                  <Navlink tagName={"Papers"} path={"/"}/>
  
  
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
      </>
    )
  }
  // Author Permissions to Post,Update,Delete, and see own Personal Papers
  else if(role === "AUTHOR"){
    return (
      <>  
        <nav className='fixed w-full top-0 left-0 px-24 py-5 bg-zinc-500 flex justify-between items-center z-50'> 
          <Link href="/" className="text-4xl text-white font-semibold select-none">TrendSight</Link> 
            <div className="navigation">
                <Navlink tagName={"Papers"} path={"/"}/>
                <Navlink tagName={"My Papers"} path={"/mypapers"}/>
                <Navlink tagName={"Upload"} path={"/upload"}/>
            </div>

            <Button onClick={logout}> Logout </Button>
        </nav>
  
        {/* Log out Functionality */}
      </>
  
    )
  }
  else{
    return (
      <>  
          <nav className='fixed w-full top-0 left-0 px-24 py-5 bg-zinc-500 flex justify-between items-center z-50'> 
            <Link href="/" className="text-4xl text-white font-semibold select-none">TrendSight</Link> 
              <div className="navigation">
                  <Navlink tagName={"Papers"} path={"/"}/>
  
  
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
      </>
  
    )
  }
  // if(role === "REVIEWER"){

  // }

}

export default Navbar