/* eslint-disable react/prop-types */
import { useParams } from 'react-router-dom';

const ArticlePage = ({ articles }) => {
  const { articleLink } = useParams(); // current URL - need it to create pages with corresponding link
  const article = articles.find((article) => article.link === articleLink); // Find the article by link

  if (!article) {
    return (
      <div className="min-h-screen flex justify-center items-center bg-custom-article-not-found bg-no-repeat bg-cover bg-center">
        <div className="p-4 text-red-600 bg-white shadow-lg rounded-lg">
          Article does not exist!
        </div>
      </div>
    );
  }

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

export default ArticlePage;
