/* eslint-disable react/prop-types */
import { Outlet } from 'react-router-dom';  // To render the child routes
import Navbar from '../components/Navbar/Navbar';
import Background from './Background.jsx'
const BaseLayout = ({ checkLogin, sendRegister }) => {
  return (
    <Background>
      <Navbar checkLogin={checkLogin} sendRegister={sendRegister} />
      <Outlet/>
    </Background>
  );
};

export default BaseLayout;