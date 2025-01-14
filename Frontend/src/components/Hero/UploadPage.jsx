import {useState} from 'react'
import {Link, useNavigate} from 'react-router'
const UploadPage = () => {
  const [title, setTitle] = useState('');
  const [author, setAuthor] = useState('');
  const [body, setBody] = useState('');
  let navigate = useNavigate();

  const uploadArticle = async (e) => {
    e.preventDefault();

    const jsonData = {
      title : title,
      body : body,
      author : author 
    }

    // Making a Post Request to Backend Server
    const res = await fetch('http://localhost:8080/article',{
      method : "POST",
      headers :{
        "Content-Type" : "application/json"
      },
      body : JSON.stringify(
        jsonData
      )
    }).then(response => {
      if(!response.ok){
        throw new Error('Network response was not ok');
      }
      return response.json();
    }).catch(error => {
      console.error("Error with Posting Article!");
    })
    // https://stackoverflow.com/questions/46820682/how-do-i-reload-a-page-with-react-router
    
    navigate("/");
    navigate(0);


  }


  return (
    <section className="bg-white rounded-xl ">
      {/* <div className="max-w-6xl mx-auto py-6">
        Upload 
      </div>
      <div className="flex-1 flex"></div> */}
      <div className="bg-gray-400 rounded-t-xl p-2 text-3xl font-medium"> Upload </div>

      <form onSubmit={uploadArticle}>
        <div className="grid grid-cols-2 gap-x-20 gap-y-10 p-4">
          {/* <input type="text" placeholder='Article Title'/> */}
          {/* <label className="text-lg"> 
            Article Title 
          </label> */}
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

          {/* <input type="file" /> */}
        </div>
        <button type="submit" className="bg-black text-white p-2 m-4 rounded-lg min-w-16"> Submit</button>


      </form>
    </section>
  )
}

export default UploadPage