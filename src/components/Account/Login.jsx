/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { FaXmark, FaEnvelope, FaLock} from 'react-icons/fa6';
import { IoPersonSharp } from 'react-icons/io5'
import { useState, useEffect} from 'react'

const Login = ({checkLogin, setShowLogin, setClosePopup}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState(''); 
    const [rememberMe, setRememberMe] = useState(false);

    const handleSubmitForm = (e) => {
        e.preventDefault();

        const credentials = {
            email,
            password,
        };

        checkLogin(credentials);

        return;
    }

  return (
    <>
        <div className="w-full p-6">
        <h2 className='text-3xl font-bold text-center' >Login</h2>
        <form onSubmit = {handleSubmitForm} id="loginForm">
            <div className="relative w-full h-12 border-b-2 border-black border-solid my-7 flex items-center justify-end">
                <span className="absolute text-xl"><FaEnvelope/></span> 
                <input 
                    className='w-full h-full bg-transparent border-none outline-none text-base text-black font-semibold pt-3 pb-1 peer'
                    type="text" 
                    required 
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <label className="absolute left-1 top-1/2 -translate-y-1/2 text-base text-black font-medium peer-focus:top-0 peer-valid:top-0 duration-300">
                    Email
                </label>
                {/* Note: Can use the invalid class property to check if an email address is invalid/valid --> Then we set this html to display/hide based in invalid/valid */}
            </div>
            <div className="relative w-full h-12 border-b-2 border-black border-solid mt-7 flex items-center justify-end">
                <span className="absolute text-xl"><FaLock/></span>
                <input 
                    className='w-full h-full bg-transparent border-none outline-none text-base text-black font-semibold pt-3 pb-1 peer'
                    type="password" 
                    required 
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <label className="absolute left-1 top-1/2 -translate-y-1/2 text-base text-black font-medium peer-focus:top-0 peer-valid:top-0 duration-300">
                    Password
                </label>
            </div>
            {/* Set some JS Code to determine if this should be shown or not */}
            {/* <div className="group peer:invalid:invisible text-red-600 mb-5"> Invalid Email or Password</div> */}

            <div className="text-sm font-medium my-3 flex justify-between">
                <label>
                    <input
                        className='mr-2' 
                        type="checkbox"
                        id="rememberMe"
                        value = {rememberMe} 
                        onChange={(e) => setRememberMe(!e.target.value)}
                    /> 
                    Remember me
                    </label >
                <a href="#" className='hover:underline'>Forgot Password?</a>
            </div>
            <button type="submit" className="w-full h-11 bg-black text-white rounded-md">Login</button>
            <div className="text-sm text-center font-medium mt-5 mb-3">
                <p>Don't have an account? <button onClick = {() => {setShowLogin(false);}} className='font-bold hover:underline'> Register</button></p>
            </div>
        </form>
        </div>
    </>
  )
}

export default Login