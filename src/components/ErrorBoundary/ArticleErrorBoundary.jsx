import { Link, useRouteError } from "react-router";
import { FaExclamationTriangle } from 'react-icons/fa';


const ErrorBoundary = () => {
  const error = useRouteError();
    console.log(error.status);


  return (
    <section className='text-center flex flex-col justify-center items-center h-96'>
      <FaExclamationTriangle className='text-red-600 text-6xl mb-4' />
      <h1 className='text-6xl font-bold mb-4'>404 Article Not Found</h1>
      <p className='text-xl mb-5'>This article does not exist, please go back and try again!</p>
      <Link
        to='/'
        className='text-white bg-slate-500 hover:bg-stone-900 rounded-md px-3 py-2 mt-4'>
        Go Back
      </Link>
    </section>
  );
};

export default ErrorBoundary;
