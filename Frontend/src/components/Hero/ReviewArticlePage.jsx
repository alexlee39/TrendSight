/* eslint-disable no-unused-vars */
/* eslint-disable react/prop-types */
import { useState } from 'react';
import { data, useNavigate, useParams, useLoaderData } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"


const ReviewArticlePage = () => {
  const { id } = useParams(); // current URL - need it to create pages with corresponding link
  const article = useLoaderData();
  const [articleStatus, setArticleStatus] = useState("");

  const getArticleDate = (article) => {
    const curDate = new Date(article.dateInEpochMS);
    return (curDate.getMonth()+1) + "/" + curDate.getDate() + "/" + curDate.getFullYear(); // curDate returns 0-11
  }

  const handleSubmit = async () =>{
    const reviewerData = {
      articleStatus : articleStatus
    }
    try{
      const res = await fetch(`http://localhost:8080/article/review/${id}`,{
        method : "POST",
        headers :{
          "Content-Type" : "application/json"
        },
        credentials : "include",
        body : JSON.stringify(reviewerData),
      })
      const data = await res.json();
    }
    catch(error){
      console.log("Internal Server Error:\n" + error);
    }
  }

  return (
    <section className="absolute w-full left-1/2 transform -translate-x-1/2 top-28">
      <div className="max-w-3xl mx-auto p-6 bg-white shadow-lg rounded-lg">

        <h1 className="text-3xl font-extrabold text-gray-900">{article.title}</h1>
          <div className="mt-2 text-sm text-gray-600">
            <p>By <span className="font-medium">{article.author}</span></p>
            <p>{getArticleDate(article)}</p>
          </div>
          <div className="grid grid-cols-2">
            <div className="my-2">{article.body}</div>
            {/* <div className="flex flex-col">
              <label htmlFor="body" className='text-lg font-medium'>Body</label>
              <textarea 
                name="body" 
                id="body" 
                className='focus:outline-none border-black border-2 rounded-lg p-3 resize-none'
              /> 
            </div> */}
            <Select onValueChange={setArticleStatus} required>
            <SelectTrigger className="w-[180px]">
              <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="PENDING">PENDING</SelectItem>
                  <SelectItem value="REJECTED">REJECTED</SelectItem>
                  <SelectItem value="PUBLISHED">PUBLISHED</SelectItem>
                </SelectContent>
            </Select>
          </div>

          <Button onClick={() => handleSubmit()} className="mt-6 mb-2"> Submit </Button>
      </div>      
    </section>
  );
};

export default ReviewArticlePage;
