import React, { useState } from "react";

const Hero = () => { // maybe use props to call Hero with new article data? can easily update table

  const [articles, setArticles] = useState([  // state to update table with new data --> present data
    { title: "Why are CS Majors working at McDonalds?", author: "Bob", date: "11/20/2024", link: "/article/0001" }, // article title needs to be long to match figma design
    { title: "Will AI take over the world?", author: "Chasy", date: "10/14/2024", link: "/article/0002" },
  ]);
  
  const newArticle = { // temp
    title: "New article for testing",
    author: "Test Author",
    date: "12/14/2024",
    link: "/article/0003"
  };
  
  const addNewArticle = () => { // react needs some kind of trigger to update states, some kinda update/refresh button to update with new data? 
    setArticles((prevArticles) => [...prevArticles, newArticle]);
  };  
  
  return (
    <div className="container mx-auto px-4 py-6 pt-8"> {/* styling using tailwind aint any easier man wtf */}
      <div className="overflow-x-auto">
        <table className="min-w-full table-auto border-collapse border border-gray-300 rounded-lg">
          <thead className="bg-gray-200">
            <tr>
              <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black w-1/2">Papers</th> {/* lil black colum sep and use new line if article is too long */}
              <th className="px-20 py-6 text-left text-lg font-bold text-gray-800 border-r border-black">Author</th>
              <th className="px-20 py-6 text-left text-lg font-bold text-gray-800">Date</th>
            </tr>
          </thead>
          <tbody className="bg-white">
            {articles.map((article, index) => (
              <tr key={index} className="border-t hover:bg-gray-100">
                <td className="px-12 py-5 text-m text-cyan-600 border-r border-black">
                  <a href={article.link} className="hover:underline">{article.title}</a>
                </td>
                <td className="px-20 py-5 text-m text-gray-800 border-r border-black">{article.author}</td>
                <td className="px-20 py-5 text-m text-gray-600">{article.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Hero;
