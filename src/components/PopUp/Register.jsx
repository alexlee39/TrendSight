import { FaXmark, FaEnvelope, FaLock} from 'react-icons/fa6';
import { IoPersonSharp } from 'react-icons/io5'
const Register = ({ setShowLogin, setClosePopup}) => {
  return (
    <>
        <span onClick = {() => setClosePopup(false)} className="icon-close"> <FaXmark/></span>
        <div className="form-box register">
            <h2>Registration</h2>
            <form id="registerForm">
                <div className="input-box">
                    <span className="icon"><IoPersonSharp/></span>
                    <input type="text" id="username" required /> 
                    <label>Name</label>
                </div>
                <div className="input-box">
                    <span className="icon"><FaEnvelope/></span>
                    <input type="text" id="reg-email" required />
                    <label>Email</label>
                </div>
                <div className="input-box">
                    <span className="icon"><FaLock/></span>
                    <input type="password" id="reg-password" required />
                    <label>Password</label>
                </div>
                <div className="wrong-credentials"> Invalid Email or Password</div>
                <div className="remember-forgot">
                    <label><input type="checkbox" id="termsCheckbox" className="terms-checkbox" /> I agree to the <a href="#" className="terms-link">terms & conditions</a></label>
                </div>                
                <button type="submit" className="btn register-btn">Register</button>
            </form>
                <div className="login-register">
                    <p>Already have an account? <button onClick = {() => setShowLogin(true)} className="login-link">Login</button></p>
                </div>
        </div>
    </>
    
  )
}

export default Register