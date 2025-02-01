import { useState, useEffect } from 'react'
import { Link } from 'react-router';
const ReviewerHomePage = ({}) => {
    const [articles, setArticles] = useState([]);
    useEffect(() => {
        const fetchPendingArticles = async () => {
          try {
            const res = await fetch('http://localhost:8080/article/review',{
              credentials : "include",
            });
            const data = await res.json();
            setArticles(data); // updates state with new db data
          }
          catch (error){
            console.log("Articles can't be fetched:\n", error);
          }
        };
        fetchPendingArticles();
      }, []);

      const getArticleDate = (article) => {
        const curDate = new Date(article.epochMillis);
        return (curDate.getMonth()+1) + "/" + curDate.getDate() + "/" + curDate.getFullYear(); // curDate returns 0-11
      }

  return (
    <>
<section className="absolute w-full max-w-5xl left-1/2 top-1/2 transform -translate-x-1/2 -translate-y-1/2 ">
      <div className="container mx-auto overflow-x-auto">
        <div
          className="overflow-y-auto" // can use tailwind custom scroll bars? 
          style={{ maxHeight: "600px", border: "1px solid #ddd", borderRadius: "5px" }} // adjust maxHeight for length
        >
          <table className="min-w-full table-auto border-collapse border border-gray-400 rounded-lg">
            <thead className="bg-gray-200">
              <tr className="bg-custom-background bg-no-repeat bg-center"> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black min-w-96 w-1/2">Articles</th> 
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Submitted</th>
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800 border-r border-black ">Reviewer</th>
                <th className="px-12 py-6 text-left text-lg font-bold text-gray-800">Status</th>
              </tr>
            </thead>
            <tbody className="bg-white"> 
              {articles.map((article, index) => ( 
                <tr key={index} className="border-t hover:bg-gray-100 ">
                  <td className="px-12 py-5 text-m text-cyan-600 border-r border-black">
                    <Link to={`/review/article/${article.id}`} className="hover:underline">
                      {article.title}
                    </Link>
                  </td>
                  <td className="text-md px-12 py-5 text-gray-800 border-r border-black">
                    <div className="flex item-center justify-between">
                      <span>{getArticleDate(article)}</span>
                      <div className="ml-2">
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
    </>
  )
}

export default ReviewerHomePage