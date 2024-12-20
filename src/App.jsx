import Navbar from './components/Navbar/Navbar.jsx'
import { useState, useEffect } from 'react'
import Hero from "./components/Hero/Papers.jsx";
//import mockData from "../mockdb/accounts.json"

const App = () => {
  // Create token/sessions to identify when users are logged in
  // const [token, setToken] = useState(null);

  const checkLogin = async(credentials) =>{
    // TESTING w/ MOCK DB Code.. TO REMOVE
    try {
      const res = await fetch(`http://localhost:5000/accounts/${credentials.email}`);
      const data = await res.json();
      console.log(data);
    } 
    catch (error) {
      console.log(error)
    }
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
    } 
    catch (error) {
      console.log(error);
    }
  }

  const [articles, setArticles] = useState([  // state to update table with new data --> present data
    // { title: "Why are CS Majors working at McDonalds?", author: "Mike O", date: "11/20/2024", link: "/article/0001" }, // article title needs to be long to match figma design
  ]);

  useEffect(() => { // triggers only once when the component is mounted (refresh/return to page)
    const fetchArticles = async () => {
      try {
        const res = await fetch('http://localhost:5000/articles');
        const articleData = await res.json();
        // W/o calling fetch/GET Request:
        // const articleData = mockData.articles; // 2 arrays from db (accounts, articles)
        setArticles(articleData); // updates state with new db data
      }
      catch (error){
        console.log("GET request error:", error);
      }
    };
    fetchArticles();
  }, []);
  
  return (
    <div className= "App">
      <Navbar checkLogin={checkLogin} sendRegister={sendRegister}/> { /* pop up is somehow stuck onto table with this arrangement (which is good) */}
      <Hero articles={articles} setArticles={setArticles} /> { /* main body component requires useState (keys have to be corresponding to column values ex. author, title, date, etc) to map into table */}
    </div>
  );
};

export default App
