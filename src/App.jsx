import Navbar from './components/Navbar/Navbar.jsx'
import { useState } from 'react'
import Hero from "./components/Hero/Papers.jsx";

const App = () => {
  // Create token/sessions to identify when users are logged in
  // const [token, setToken] = useState(null);

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
      await fetch("http://localhost:5000/accounts", {
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
  }
  const [articles, setArticles] = useState([  // state to update table with new data --> present data
    { title: "Why are CS Majors working at McDonalds?", author: "Alex L", date: "11/20/2024", link: "/article/0001" }, // article title needs to be long to match figma design
    { title: "Will AI take over the world?", author: "Mike O", date: "10/14/2024", link: "/article/0002" },
  ]);

  return (
    <div className= "App">
      <Hero articles={articles} setArticles={setArticles} />
      <Navbar checkLogin={checkLogin} sendRegister={sendRegister}/>
    </div>
  );
};

export default App