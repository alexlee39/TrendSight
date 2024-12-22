/* eslint-disable react/prop-types */
import { data, useNavigate, useParams, useLoaderData } from 'react-router-dom';

const ArticlePage = ({ articles }) => {
  const { id } = useParams(); // current URL - need it to create pages with corresponding link
  const article = useLoaderData();

  return (
    <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg">
      {/* Title */}
      <h1 className="text-3xl font-extrabold text-gray-900">{article.title}</h1>
      {/* Author and Date */}
      <div className="mt-2 text-sm text-gray-600">
        <p>By <span className="font-medium">{article.author}</span></p>
        <p>{article.date}</p>
      </div>
      {/* Content */}
      <div className="mt-6 text-base text-gray-800 leading-relaxed">
        <p>{article.content}</p>
      </div>
    </div>
  );
};

const articleLoader = async ({ params }) => {
  const res = await fetch(`http://localhost:5000/articles/${params.id}`);
  if (!res.ok) {
      throw new Response("Article Not Found", { status: res.status });
  }
  return await res.json();

  // if I run the code below, if I don't throw any errors in the catch block, I would effective have 
  // article be undefined so we could handle 404 not found with 

  // try {
  //   const res = await fetch(`http://localhost:5000/articles/${params.id}`);
  //   if (!res.ok) {
  //     throw new Response("Article Not Found", { status: res.status });
  //   }
  //   return await res.json();
  // } catch (error) {
  //   console.error(error);
  // }
};

export {ArticlePage as default, articleLoader};
