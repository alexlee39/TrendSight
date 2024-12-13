import Navbar from './components/Navbar/Navbar.jsx'
import { useState } from 'react'

const App = () => {
  // Create token/sessions to identify when users are logged in
  const [token, setToken] = useState(null);

  const checkLogin = async(credentials) =>{
    // TESTING w/ MOCK DB Code.. TO REMOVE
    try {
      const res = await fetch(`http://localhost:5000/accounts/${credentials.email}`);
      const data = await res.json(); 
      console.log(data); 
    } catch (error) {
      console.log(error)
    } 
    // const res = await fetch("http://localhost:5000/accounts/login", {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(credentials),
    // });
    // const data = await res.json();
    // console.log("HTTP Login POST Req finished");
    // console.log(data);
    return;
  }

  const sendRegister = async(accDetails) => {
    // CODE PURELY FOR TESTING with MOCKDB
    try {
      const res = await fetch("http://localhost:5000/accounts", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(accDetails),
      });
      console.log('Mock DB works???');
    } catch (error) {
      console.log(error);   
    }

    // const res = await fetch("http://localhost:5000/accounts/register", {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify(accDetails),
    // });
    // console.log('SEND REGISTRATION LUL');
  }


  return (
    <Navbar checkLogin={checkLogin} sendRegister={sendRegister}/>
  )
}

export default App