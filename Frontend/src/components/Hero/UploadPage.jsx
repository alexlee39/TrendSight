import {useState} from 'react'
import {Link, useNavigate} from 'react-router'
import { Button } from '@/components/ui/button'
import { useToast } from '@/hooks/use-toast'
const UploadPage = () => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [body, setBody] = useState('');
  const [file, setFile] = useState('');
  let { toast } = useToast();
  let navigate = useNavigate();

  const handlePdfUpload = async (file) => {
    const pdf = file.files[0];
    try{
      let pdfBody = await convertPdfToText(pdf);
      setFile(pdf.name);
      setBody(pdfBody);

    }
    catch(error){
      throw new Error("Something went wrong. Please try again later");
    }
  }
 
  const uploadArticle = async (e) => {
    e.preventDefault();
    const jsonData = {
      title : title,
      body : body,
      author : author 
    }

    // Making a Post Request to Backend Server
    postArticle(jsonData);
  }

  const postArticle = async(articleData) => {
    try{
      const res = await fetch('http://localhost:8080/article/author',{
        method : "POST",
        headers :{
          "Content-Type" : "application/json"
        },
        body : JSON.stringify(
          articleData
        ),
        credentials : "include"
      });
      toast({
        variant : res.ok ? "default" : "destructive",
        title : res.ok ? "Added article Successfully" : "Article creation failed"
      
      });
      setTimeout(() => {
        if(res.ok){
          navigate("/");
        }
      }, 1000);
    }

    catch(error) {
      console.error("Internal Server Error");
      console.log(error);
    }
  }

  const convertPdfToText = async (file) =>{
    const formData = new FormData();
    formData.append("file", file);
    try{
      const response = await fetch('http://localhost:8080/article/file', {
        method : "POST",
        body: formData,
        credentials : "include",
      });
  
      if(!response.ok){
        console.error(`Error uploading file: ${file.name}`);
        throw new Error(`Server error: ${response.status}`);
      }

      const data = await response.text();
      console.log(data);
      console.log(typeof(data));
      return data
    }
    catch(error){
        throw new Error("Something went wrong while processing pdf. Please try again later");
    }
  } 

  return (
    <section className="bg-white rounded-xl ">
      <div className="bg-gray-400 rounded-t-xl p-2 text-3xl font-medium"> Upload </div>
      <form onSubmit={uploadArticle}>
        <div className="grid grid-cols-2 gap-x-20 gap-y-10 p-4">
          <div className="flex flex-col m-2">
            <label htmlFor="title" className='p-2'> Article Title</label>
            <input 
              type="text" 
              name="title"
              placeholder = 'Article Title' 
              className={`focus:outline-none ${title.trim() ? 'border-blue-500' : 'border-red-500'} border-2 rounded-md p-2`}
              // ${!author.trim() ? 'border-red-500' : 'border-blue-500'}
              value= {title}
              onChange={(e) => setTitle(e.target.value)}
            />
          </div>

          <div className="flex flex-col m-2">
            <label htmlFor="author" className='p-2'> Author Name(s) </label>
            <input 
              type="text" 
              name="author"
              placeholder = 'Author Name' 
              className={`focus:outline-none ${ author.trim() ? 'border-blue-500' : 'border-red-500'} border-2 rounded-md p-2 `} //placeholder:text-black placeholder:font-thin'
              value={author}
              onChange={(e) => setAuthor(e.target.value)}
            />
          </div>

          <div className="flex flex-col">
            <label htmlFor="body" className='text-lg font-medium'>Body</label>
            <textarea 
              name="body" 
              id="body" 
              className={`focus:outline-none ${ body.trim() ? 'border-blue-500' : 'border-red-500'} border-2 rounded-lg p-3 min-w-110 min-h-80 resize-none`}
              value = {body}
              onChange={(e) => setBody(e.target.value)}
            /> 
          </div>
          
          <div className="flex-1 mx-2 my-4 p-2 justify-between">
            <label htmlFor="file-input" className='bg-red-600 p-2 text-lg font-medium border-2 rounded-md cursor-pointer'>
              Upload PDF 
            </label>
            <p className='py-2'> {file ? file : "No file selected"} </p>
            <input 
              id="file-input" 
              type="file" 
              className='hidden' 
              accept=".pdf"
              onChange={(e) => {handlePdfUpload(e.target)}}
            />
          </div>

        </div>
        <button type="submit" className="bg-black text-white p-2 m-4 rounded-lg min-w-16"> Submit</button>

      </form>
    </section>
  )
}

export default UploadPage