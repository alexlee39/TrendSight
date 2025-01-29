/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { data, useNavigate, useParams, useLoaderData } from 'react-router-dom';

const ArticlePage = () => {
  //const { id } = useParams(); // current URL - need it to create pages with corresponding link
  const article = useLoaderData();

  /// processing paragraphs, quotes and links accordingly based off mockDB format
  const renderContent = () => {
    let paragraphArray = article.content.paragraphs;
    let quoteArray = article.content.quotes;
    let linkArray = article.content.links;
    return paragraphArray.map((paragraph, index) => {
      let paragraphText = paragraph.text;
      // processing #quote# placeholder if any
      if (paragraphText.includes("#quote#") && paragraph.quote !== undefined) {
        paragraphText = paragraphText.replace("#quote#", 
          `<span class="font-serif italic text-gray-600">"${quoteArray[paragraph.quote]}"</span>`
        );
      }
      // processing #link# placeholder if any
      if (paragraphText.includes("#link#") && paragraph.link !== undefined) {
        paragraphText = paragraphText.replace("#link#", 
          `<a href="${linkArray[paragraph.link]}" class="text-blue-500">${linkArray[paragraph.link]}</a>`
        );
      }
      return (
        <div key={index} className="mb-5">
          {/* paragraph text stylign */}
          <p className="font-serif leading-relaxed mb-4" dangerouslySetInnerHTML={{ __html: paragraphText }} />
        </div>
      );
    });
  };

  return (
    <section className="absolute w-full left-1/2 transform -translate-x-1/2 top-28">
      <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg">
        {/* Title Styling*/}
        <h1 className="text-3xl font-extrabold text-gray-900">{article.title}</h1>
        {/* Author and Date Styling*/}
        <div className="mt-2 text-sm text-gray-600">
          <p>By <span className="font-medium">{article.author}</span></p>
          <p>{article.date}</p> {/* data not properly formatted in backend */}
        </div>
        {/* Content rendering, and spacing*/}
        {/* <div className="mt-4">{renderContent()}</div> */}
        <div className="mt-4">{article.body}</div>
      </div>      
    </section>
  );
};

const articleLoader = async ({ params }) => {
  const res = await fetch(`http://localhost:8080/article/${params.id}`);
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
