import Navbar from './components/Navbar/Navbar.jsx'
import { useState, useEffect } from 'react'
import Papers from "./components/Hero/Papers.jsx";
//import mockData from "../mockdb/accounts.json"

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import ArticlePage from "./components/Hero/ArticlePage.jsx";

const App = () => {
  // Create token/sessions to identify when users are logged in
  // const [token, setToken] = useState(null);
  // state to update table with new data --> present data
  const [articles, setArticles] = useState([]);

  useEffect(() => { // triggers only once when the component is mounted/re-rendered (refresh/Papers button)
    const fetchArticles = async () => {
      try {
        const res = await fetch('http://localhost:5000/articles');
        const articleData = await res.json();
        // W/o calling fetch/GET Request:
        // const articleData = mockData.articles; // 2 arrays from db (accounts, articles)
      
        setArticles(articleData); // updates state with new db data

      }
      catch (error){
        console.log("articles werent extracted properly\n", error);
      }
    };
    fetchArticles();
  }, []);

  const checkLogin = async(credentials) =>{
    // TESTING w/ MOCK DB Code.. TO REMOVE
    try {
      const res = await fetch(`http://localhost:5000/accounts/${credentials.email}`);
      const data = await res.json();
      console.log(data);
    } catch (error) {
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
    } catch (error) {
      console.log(error);
    }
  }

  return (
    <div className="flex justify-center items-center min-h-screen bg-custom-background bg-no-repeat bg-cover bg-center">
    <Router>
        <Navbar checkLogin={checkLogin} sendRegister={sendRegister}/>
        <Routes>
          {/* Route for homepage showing the Papers component with the list of articles */}
          <Route path="/" element={<Papers articles={articles} setArticles={setArticles} />} />
          {/* dynamic route for individual article pages with corresponding component */}
          <Route path="/papers/:articleLink" element={<ArticlePage articles={articles} />} />
        </Routes>          
    </Router>
    </div> 
  );
};

export default App
