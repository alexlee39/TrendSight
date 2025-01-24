/* eslint-disable react/prop-types */
import { Outlet } from 'react-router-dom';  // To render the child routes
import Navbar from '../components/Navbar/Navbar';
import Background from './Background.jsx';
import { Toaster } from "@/components/ui/toaster";
const BaseLayout = ({ checkLogin, sendRegister, role}) => {
  return (
    <Background>
      <Navbar checkLogin={checkLogin} sendRegister={sendRegister} role={role} />
      <Outlet/>
      <Toaster/>
    </Background>
  );
};

export default BaseLayout;