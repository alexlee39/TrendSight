import { useState, useEffect, createContext } from 'react'
import { useNavigate, Route, RouterProvider, createBrowserRouter, createRoutesFromElements} from 'react-router';


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
  const preloadedRole = sessionStorage.getItem("role");
  const [role, setRole] = useState(preloadedRole || null);

  const ROLES = {
    AUTHOR: "AUTHOR",
    REVIEWER: "REVIEWER",
    ADMIN: "ADMIN",
  }


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
        const data = await res.json();
        console.log(data.role);
        setRole(data.role);
        sessionStorage.setItem("role", data.role);
        return {
            'success' : true,
            'message' : "Login Successful"
        }
      }
      else{
        console.log("Login Failed!");
        return {
            'success' : false, 
            'message' : "Incorrect Username or Password."
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
      const res = await fetch("http://localhost:8080/register", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(accDetails),
        credentials : 'include'
      });
      const data = await res.json();
      if(res.ok){
        console.log("Account created!");
        return {
          'success' : true,
          'successMsg' : 'Created account successfully',
          'message' : `Created the account: ${data.email}`,
        }
      }
      else{
        console.log("User already exists!");
        console.log(`User with the email ${data.email} already exists`);
        return {
          'success' : false,
          'successMsg' : 'Registering Account failed',
          'message' : `User with the email: "${data.email}" already exists`,
        }
      }
    } catch (error) {
      console.log("Server error");
      console.log(error);
    }
  }

  const logout = async() => {
    try{
      const res = await fetch("http://localhost:8080/logout",{
        method : "POST",
        credentials : "include",
      })
      if(res.ok){
        sessionStorage.clear();
        setRole('');
        console.log("LOGOUT SUCCESS");
      }
      else{
        console.log("LOGOUT FAILED");
      }
    }
    catch(error){
      console.log("INTERVAL SERVER ERROR: " + error);
    }
  }

  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path = "/" element={ <BaseLayout checkLogin={checkLogin} sendRegister={sendRegister} role={role} logout={logout}/>}>
        <Route index element={<Papers role={role}/>} />
        <Route path = "papers/:id" element={<ArticlePage />} loader={articleLoader}  errorElement={<ErrorBoundary />}/>
        <Route path = "edit/:id" element = {<EditArticlePage/>} loader={articleLoader}/>
        <Route path = "upload" element={<UploadPage/>}/>
        <Route path = "mypapers" element={<MyPapers />}/>

        <Route path = "*" element={<NotFoundPage/>}/>
      </Route>
    )
  )
  return <RouterProvider router={router}/>
};

export default App
