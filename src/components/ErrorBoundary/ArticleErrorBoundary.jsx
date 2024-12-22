import { Link, useRouteError } from "react-router";
import { FaExclamationTriangle } from 'react-icons/fa';


const ErrorBoundary = () => {
  const error = useRouteError();
    console.log(error.status);


  return (
    <section className="text-center flex flex-col justify-center items-center min-h-96">
      <FaExclamationTriangle className="text-yellow-400 text-6xl mb-2"/>
      <h2 className="text-red-500 text-4xl mb-3">404 Article Not Found</h2>
      <Link to="/" className="text-2xl bg-slate-500 rounded-xl py-2 px-4 hover:bg-indigo-800">
        Go Back
      </Link>
    </section>
  );
};

export default ErrorBoundary;
