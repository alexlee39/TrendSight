/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { data, useNavigate, useParams, useLoaderData } from 'react-router-dom';

const ArticlePage = () => {
  const { id } = useParams(); // current URL - need it to create pages with corresponding link
  const article = useLoaderData();

  // processing paragraphs, quotes and links accordingly based off mockDB format
  const renderContent = () => {
    return article.content.paragraphs.map((paragraph, index) => {
      let paragraphText = paragraph.text;
      // processing #quote# placeholder if any
      if (paragraphText.includes("#quote#") && paragraph.quote !== undefined) {
        paragraphText = paragraphText.replace("#quote#", `"${article.content.quotes[paragraph.quote]}"`);
      }
      // processing #link# placeholder if any
      if (paragraphText.includes("#link#") && paragraph.link !== undefined) {
        paragraphText = paragraphText.replace("#link#", `<a href="${article.content.links[paragraph.link]}" class="text-blue-500">${article.content.links[paragraph.link]}</a>`);
      }

      return (
        <div key={index} className="mb-6">
          {/* Render the paragraph with the replaced content */}
          <p className="text-lg leading-relaxed mb-4" dangerouslySetInnerHTML={{ __html: paragraphText }} />
        </div>
      );
    });
  };

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
      <div className="mt-6 font-serif text-gray-800">{renderContent()}</div>
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
