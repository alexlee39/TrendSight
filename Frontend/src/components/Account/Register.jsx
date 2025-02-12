/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { FaXmark, FaEnvelope, FaLock} from 'react-icons/fa6';
import { IoPersonSharp } from 'react-icons/io5'
import { useState } from 'react'
import { useNavigate } from 'react-router';
import { toast, useToast } from '@/hooks/use-toast';
import { Description } from '@radix-ui/react-toast';

const Register = ({ sendRegister, setShowLogin, setClosePopup}) => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isChecked, setChecked] = useState(false);
    const [regFailedMsg, setRegFailedMsg] = useState('');
    const { toast } = useToast();
    let navigate = useNavigate();


    // Use isChecked to make a custom required function that we could pass to display some text
    // telling the user to check the box before proceeding
    
    const handleSubmit = async(e) =>{
        e.preventDefault();
        const accDetails = {
            username,
            email,
            password,
        };
        const response = await sendRegister(accDetails);
        if(!response.success){
            setRegFailedMsg(response.message);
        }
        toast({
            title: response.successMsg,
            description : response.message,
            variant : response.success ? "default" : "destructive",
        });
        setTimeout(() => {
            if(response.success){
                setClosePopup();
            }
        }, 2000)

    }
    return (
    <>
        <div className="w-full py-4 px-6">
            <h2 className='text-3xl font-bold text-center'>Registration</h2>
            <form onSubmit = {handleSubmit} id="registerForm">
                <div className="relative w-full h-12 border-b-2 border-black border-solid my-7 flex items-center justify-end">
                    <span className="absolute text-xl"><IoPersonSharp/></span>
                    <input 
                        className='w-full h-full bg-transparent border-none outline-none text-base text-black font-semibold pt-3 pb-1 peer'
                        type="text" 
                        required 
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    /> 
                    <label className="absolute left-1 top-1/2 -translate-y-1/2 text-base text-black font-medium peer-focus:top-0 peer-valid:top-0 duration-300">
                        Name
                    </label>
                </div>
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
                {/* <div className="wrong-credentials"> Invalid Email or Password</div> */}
                <div className="text-md my-2 text-red-500">
                    {regFailedMsg}
                </div>
                
                <div className="text-sm font-medium mb-4">
                    <label>
                        <input 
                            className='mr-2'
                            type="checkbox" 
                            onClick={() => setChecked(prev => !prev)}
                            required
                            value={isChecked}
                            onChange={(e) => setChecked(e.target.checked)}
                        /> 
                        I agree to the <a href="#" className="font-bold hover:underline">terms & conditions</a>
                    </label>
                </div>                
                <button type="submit" className="w-full h-11 bg-black text-white rounded-md">Register</button>
            </form>
                <div className="text-sm text-center font-medium mt-5 mb-3">
                    <p>Already have an account? <button onClick = {() => setShowLogin(true)} className="font-bold hover:underline">Login</button></p>
                </div>
        </div>
    </>
    
  )
}

export default Register