import {useState} from 'react'
import {useNavigate, useLoaderData, useParams} from 'react-router-dom'
import { Button } from "@/components/ui/button"
import { useToast } from '@/hooks/use-toast'

const EditArticlePage = () => {
  const {id} = useParams();
  const article = useLoaderData();
  let navigate = useNavigate();
  let { toast } = useToast();



  const [title, setTitle] = useState(article.title);
  const [author, setAuthor] = useState(article.author);
  const [body, setBody] = useState(article.body);


  const updateArticleData = async(e) => {
    e.preventDefault();
    const jsonData = {
      title : title,
      body : body,
      author : author 
    }

    updateArticle(jsonData);
  }

  const updateArticle = async(articleData) => {
    try{
      const res = await fetch(`http://localhost:8080/article/${id}`,{

        method : "PUT",
        headers :{
          "Content-Type" : "application/json"
        },
        body : JSON.stringify(
          articleData  
        ),
        credentials : "include",
        });

        toast({
          variant : res.ok ? "default" : "destructive",
          title : res.ok ? "Updated article Successfully" : "Article update failed"
        
        });
        setTimeout(() => {
            if(res.ok){
              navigate("/");
            }
          }, 1000);    

        }
    catch(error) {
      console.log("Internal Server Error: " + error);
    }
  }


  return (
    <section className="bg-white rounded-xl ">
      <div className="bg-gray-400 rounded-t-xl p-2 text-3xl font-medium"> Upload </div>
      <form onSubmit={updateArticleData}>
        <div className="grid grid-cols-2 gap-x-20 gap-y-10 p-4">
        <div className="flex flex-col m-2">
            <label htmlFor="title" className='p-2'> Article Title</label>
            <input 
              type="text" 
              name="title"
              placeholder = 'Article Title' 
              className='focus:outline-none border-blue-500 border-2 rounded-md p-2 '
              // id = "title"
              value= {title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>
          <div className="flex flex-col m-2">
            <label htmlFor="author" className='p-2'> Author Names </label>
            <input 
              type="text" 
              name="author"
              placeholder = 'Author Name' 
              className='focus:outline-none border-blue-500 border-2 rounded-md p-2 ' //placeholder:text-black placeholder:font-thin'
              value={author}
              onChange={(e) => setAuthor(e.target.value)}
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="body" className='text-lg font-medium'>Body</label>
            <textarea 
              name="body" 
              id="body" 
              className='focus:outline-none border-black border-2 rounded-lg p-3 min-w-110 min-h-80 resize-none'
              value = {body}
              onChange={(e) => setBody(e.target.value)}
            /> 
            
          </div>
        </div>
        <Button onClick={() => navigate("/")} className="p-2 m-4"> Return </Button>
        <button type="submit" className="bg-black text-white p-2 my-4 rounded-lg min-w-16"> Save </button>


      </form>
    </section>
  )
}

export default EditArticlePage