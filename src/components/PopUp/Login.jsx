import { FaXmark, FaEnvelope, FaLock} from 'react-icons/fa6';
import { IoPersonSharp } from 'react-icons/io5'

const Login = ({setShowLogin, setClosePopup}) => {
    // const [email, setEmail] 
  return (
    <>
        <span onClick = {() => setClosePopup(false)} className="icon-close"> <FaXmark/></span>
        <div className="form-box login">
        <h2>Login</h2>
        <form id="loginForm">
            <div className="input-box">
                <span className="icon"><FaEnvelope/></span>
                <input type="text" id="login-email" required />
                <label>Email</label>
            </div>
            <div className="input-box">
                <span className="icon"><FaLock/></span>
                <input type="password" id="login-password" required />
                <label>Password</label>
            </div>
            <div className="wrong-credentials"> Invalid Email or Password</div>

            <div className="remember-forgot">
                <label><input type="checkbox" id="rememberMe" /> Remember me</label >
                <a href="#">Forgot Password?</a>
            </div>
            <button type="submit" className="btn login-btn">Login</button>
            <div className="login-register">
                <p>Don't have an account? <button onClick = {() => {setShowLogin(false);}} className = "register-link"> Register</button></p>
            </div>
        </form>
        </div>
    </>
  )
}

export default Login