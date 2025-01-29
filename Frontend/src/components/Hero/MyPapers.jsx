/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import React, { useState, useEffect } from "react";
import { Link , useNavigate} from 'react-router-dom';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Button } from "@/components/ui/button"
import { AirVent } from "lucide-react";

const MyPapers = ({}) => { // maybe use props to call Hero with new article data? can easily update table
  const [articles, setArticles] = useState([]);
  const [sortKey, setSortKey] = useState("date");
  let navigate = useNavigate();

  useEffect(() => {
    const fetchArticles = async () => {
      try {
        const res = await fetch('http://localhost:8080/user/article',{
          credentials : "include",
        });
        const articleData = await res.json();
        setArticles(articleData); // updates state with new db data
      }
      catch (error){
        console.log("articles werent extracted properly\n", error);
      }
    };
    fetchArticles();
  }, []);

  function sortArticles() {
    if (sortKey === "date") { // will be implmenting Most Recent, and Oldest options instead of basic DATE (default should be most recent)
      return [...articles].sort((a, b) => { // sort shallow copy ...
        const dateA = new Date(a.date); // parse str into object
        const dateB = new Date(b.date); 
        return dateB - dateA; // descending order, vice verses
      });
    }
    else if (sortKey === "author"){
      // can set up more options in future
    }
    return articles; // default
  }
  const sortedArticles = sortArticles(); // updates every rerender

  const getArticleDate = (article) => {
    const curDate = new Date(article.epochMillis);
    return (curDate.getMonth()+1) + "/" + curDate.getDate() + "/" + curDate.getFullYear(); // curDate returns 0-11
  }
  const directToEditArticlePg = (article) => {
    navigate(`/edit/${article.id}`);
  }
  const deleteArticle = async (article) => {
      const res = await fetch(`http://localhost:8080/article/${article.id}`,{
        method : "DELETE"
      }).then(response => {
        if(!response.ok){
          throw new Error('DELETE Network response was not ok');
        }
        return response.json();
      }).catch(error => {
        console.error("Error with Deleting Article!");
      })
      //navigate("/");
      location.reload();
    }  

  return (
    <section className="absolute w-full max-w-5xl left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 ">
      <div className="container mx-auto overflow-x-auto">
        <div className="flex justify-end mb-2">
        <div className="relative inline-block text-left w-40">
        <DropdownMenu >
            <DropdownMenuTrigger className="inline-flex justify-center w-full rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none">
              Sort by: {sortKey === "none" ? "None" : "Date"} 
              <svg
                className="-mr-1 ml-2 h-5 w-5"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 20 20"
                fill="currentColor"
                aria-hidden="true">
              <path
                fillRule="evenodd"
                d="M5.23 7.21a.75.75 0 011.06.02L10 10.94l3.72-3.72a.75.75 0 111.06 1.06l-4 4a.75.75 0 01-1.06 0l-4-4a.75.75 0 01.02-1.06z"
                clipRule="evenodd"/>
              </svg>
            </DropdownMenuTrigger>
            <DropdownMenuContent className="w-40">
              <DropdownMenuItem 
                className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 w-full"
                onClick={() => {setSortKey("date") }}>
                  Most Recent
              </DropdownMenuItem>
              <DropdownMenuItem 
                className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                onClick={() => {setSortKey("none") }}>
                  No Sorting
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
        </div>
        <div
          className="overflow-y-auto" // can use tailwind custom scroll bars? 
          style={{ maxHeight: "600px", border: "1px solid #ddd", borderRadius: "5px" }} // adjust maxHeight for length
        >
          <table className="min-w-full table-auto border-collapse border border-gray-400 rounded-lg">
            <thead className="bg-gray-200">
              <tr className="bg-custom-background bg-no-repeat bg-center"> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black min-w-96 w-1/2">MyPapers</th> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Submitted</th>
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Reviewer</th>
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800">Status</th>
              </tr>
            </thead>
            <tbody className="bg-white"> 
              {sortedArticles.map((article, index) => ( 
                <tr key={index} className="border-t hover:bg-gray-100 ">
                  <td className="px-12 py-5 text-m text-cyan-600 border-r border-black">
                    <Link to={`/edit/${article.id}`} className="hover:underline">
                      {article.title}
                    </Link>
                  </td>
                  <td className="text-md px-12 py-5 text-gray-800 border-r border-black">
                    <div className="flex item-center justify-between">
                      <span>{getArticleDate(article)}</span>
                      <div className="ml-2">
                        {/* <DropdownMenu>
                          <DropdownMenuTrigger className="text-sm font-bold border-black border-2 rounded-sm px-1 hover:bg-red-200">...</DropdownMenuTrigger>
                          <DropdownMenuContent>
                            <DropdownMenuItem onClick={() => directToEditArticlePg(article)}>Edit</DropdownMenuItem>
                            <DropdownMenuItem onClick={() => deleteArticle(article)}>Delete</DropdownMenuItem>
                          </DropdownMenuContent>
                        </DropdownMenu> */}
                      </div>
                    </div>
                  </td>
                  <td className="px-12 py-5 text-md text-gray-800 border-r border-black">{article.author}</td>
                  <td className="px-12 py-5 text-md text-orange-800"> {article.articleStatus}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    </section>
  );
};

export default MyPapers;

