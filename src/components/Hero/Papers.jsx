/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

const Hero = ({ articles, setArticles }) => { // maybe use props to call Hero with new article data? can easily update table
  const [dropDown, setDropDown] = useState(true);
  const [sortKey, setSortKey] = useState("none")

  let sortedArticles = articles
  if (sortKey === "date") { // sort by date state
    sortedArticles = [...articles].sort((a, b) => { // temp article data to modify for date
      const dateA =  new Date(a.date) // need objects to compare and sort
      const dateB =  new Date(b.date)
      return dateB - dateA;
    });
  }

  const toggleDropDown = () => setDropDown(!dropDown); // button for closing or opening sort
  
  // const newArticle = { // temp
  //   title: "Why can CS majors not get a girlfriend?",
  //   author: "Jeevithan M",
  //   date: "12/14/2024",
  //   link: "/article/0003"
  // };

  // const addNewArticle = () => { // react needs some kind of trigger to update states, some kinda update/refresh button to update with new data? 
  //   setArticles((prevArticles) => [...prevArticles, newArticle]);
  // };  
  
  //comments/notes (cant add comments inside jsx):
  // {/* col names - not responsive at ipad level and lower */}
  // {/* w-1/2 for new line if data too long */}
  // {/* dict data mapped out onto table */}
  // {/* edit max-w-4xl to style table width better, manually set up center because of absolute (table shouldnt move up now) */}

  return (
    <div className="absolute w-full max-w-4xl" style={{ transform: "translate(-50%, -50%)" }}>
      <div className="container mx-auto overflow-x-auto">

        <div className="flex justify-end mb-4">
          <div className="relative inline-block text-left">
            <button
            onClick={toggleDropDown}
            className="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none">
              Sort by: {sortKey === "none" ? "None" : "Date"}
              <svg
                className ="-mr-1 ml-2 h-5 w-5"
                xmlns ="http://www.w3.org/2000/svg"
                viewBox ="0 0 20 20"
                fill ="currentColor"
                aria-hidden ="true">
                <path
                  fillRule ="evenodd"
                  d ="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.72-3.72a.75.75 0 111.06 1.06l-4 4a.75.75 0 01-1.06 0l-4-4a.75.75 0 01.02-1.06z"
                  clipRule ="evenodd"/>
              </svg>
            </button>
            {!dropDown && (
              <div
                className ="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 focus:outline-none"
                role = "menu"
                aria-orientation ="vertical"
              >
                <div className="py-1" role = "none">
                  <button
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 w-full text-left"
                    role = "menuitem"
                    onClick={() => {
                      setSortKey("none");
                      setDropDown(true); 
                    }}
                  >
                    No Sorting
                  </button>
                  <button
                    className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 w-full text-left"
                    role="menuitem"
                    onClick={() => {
                      setSortKey("date");
                      setDropDown(true);
                    }}
                  >
                    Sort by Date
                  </button>
                </div>
              </div>
            )}
          </div>
        </div>
        
        <div
          className="overflow-y-auto" // can use tailwind custom scroll bars? 
          style={{ maxHeight: "600px", border: "1px solid #ddd", borderRadius: "5px" }} // adjust maxHeight for length
        >
          <table className="min-w-full table-auto border-collapse border border-gray-400 rounded-lg">
            <thead className="bg-gray-200">
              <tr> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black min-w-96 w-1/2">Papers</th> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Author</th>
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800">Date</th>
              </tr>
            </thead>
            <tbody className="bg-white"> 
              {sortedArticles.map((article, index) => ( 
                <tr key={index} className="border-t hover:bg-gray-100">
                  <td className="px-12 py-5 text-m text-cyan-600 border-r border-black">
                    <a href={article.link} className="hover:underline">{article.title}</a>
                  </td>
                  <td className="px-12 py-5 text-m text-gray-800 border-r border-black">{article.author}</td>
                  <td className="px-12 py-5 text-m text-gray-600">{article.date}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default Hero;

