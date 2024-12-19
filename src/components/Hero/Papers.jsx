/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
// eslint-disable-next-line no-unused-vars
import React, { useState } from "react";

const Hero = ({ articles, setArticles }) => { // maybe use props to call Hero with new article data? can easily update table

  // const newArticle = { // temp
  //   title: "Why can CS majors not get a girlfriend?",
  //   author: "Jeevithan M",
  //   date: "12/14/2024",
  //   link: "/article/0003"
  // };

  // const addNewArticle = () => { // react needs some kind of trigger to update states, some kinda update/refresh button to update with new data? 
  //   setArticles((prevArticles) => [...prevArticles, newArticle]);
  // };  
  
  // used chatty bot to add a css line inside tailwind...... 
  return (
    // <section className="absolute w-full max-w-4xl top-1/2 -right-1/2" style={{ transform: "translate(-50%, -50%)" }}> {/* edit max-w-4xl to style table width better, manually set up center because of absolute (table shouldnt move up now) */}
    <section className="absolute w-full max-w-4xl left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2">      
      <div className="container mx-auto overflow-x-auto">
        <table className="min-w-full table-auto border-collapse border border-gray-400 rounded-lg">
          <thead className="bg-gray-200">
            <tr> {/* col names - not responsive at ipad level and lower */}
              <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black min-w-96 w-1/2">Papers</th> {/* w-1/2 for new line if data too long */}
              <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Author</th>
              <th className="px-12 py-6 text-left text-lg font-bold text-gray-800">Date</th>
            </tr>
          </thead>
          <tbody className="bg-white"> {/* dict data mapped out onto table */}
            {articles.map((article, index) => ( 
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
    </section>
  );
};

export default Hero;

