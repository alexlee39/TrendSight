import { useState, useEffect } from 'react'
import { Route, RouterProvider, createBrowserRouter, createRoutesFromElements} from 'react-router';


import Papers from "./components/Hero/Papers.jsx";
import ArticlePage, {articleLoader} from "./components/Hero/ArticlePage.jsx";
import BaseLayout from './layout/BaseLayout.jsx';
import NotFoundPage from './pages/NotFoundPage.jsx';
import ErrorBoundary from './components/ErrorBoundary/ArticleErrorBoundary.jsx';
import UploadPage from './components/Hero/UploadPage.jsx';
import MyPapers from './components/Hero/MyPapers.jsx';
import EditArticlePage from './components/Hero/EditArticlePage.jsx';

const App = () => {
  // state to update table with new article data
  const [articles, setArticles] = useState([]);

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const res = await fetch('http://localhost:8080/article');
        const articleData = await res.json();
        setArticles(articleData); // updates state with new db data
      }
      catch (error){
        console.log("articles werent extracted properly\n", error);
      }
    };
    fetchArticles();
  }, []);

  const checkLogin = async(credentials) =>{
    try {
      const res = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
        credentials : 'include'
      });
      if(res.ok){
        console.log("Login Success");
        return {
            'success' : true,
            'message' : "Login Successful"
        }
      }
      else{
        console.log("Login Failed!");
        return {
            'success' : false, 
            'message' : "Login Failed. Incorrect Username or Password."
        }
      }
    } catch (error) {
      console.log(error)
      return {
            'success' : false, 
            'message' : "Internal Server Error. Please try again later."
      }
    }
  }

  const sendRegister = async(accDetails) => {
    try {
      await fetch("http://localhost:8080/register", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(accDetails),
      });
      //console.log('Mock DB works???');
    } catch (error) {
      console.log(error);
    }
  }

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path = "/" element={ <BaseLayout checkLogin={checkLogin} sendRegister={sendRegister} />}>
        <Route index element={<Papers articles={articles} setArticles={setArticles} />}/>
        <Route path = "papers/:id" element={<ArticlePage />} loader={articleLoader}  errorElement={<ErrorBoundary />}/>
        <Route path = "edit/:id" element = {<EditArticlePage/>} loader={articleLoader}/>
        <Route path = "upload" element={<UploadPage/>}/>
        <Route path = "mypapers" element={<MyPapers articles={articles} />}/>

        <Route path = "*" element={<NotFoundPage/>}/>
      </Route>
    )
  )
  return <RouterProvider router={router}/>
};

export default App
