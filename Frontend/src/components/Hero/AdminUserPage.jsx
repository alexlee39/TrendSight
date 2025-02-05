import {useState, useEffect} from 'react'
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { toast, useToast } from '@/hooks/use-toast';



const AdminUserPage = () => {
  const [users, setAllUsers] = useState([]);
  const [email, setEmail] = useState();
  const [password, setPassword] = useState();
  const [role, setRole] = useState();
  const { toast } = useToast();

  const handleCreateUser = async () =>{
    const userDetails = {
      email, 
      password,
      role,
    }
    console.log(userDetails);
    console.log(JSON.stringify(userDetails));

    try {
      const res = await fetch("http://localhost:8080/admin/user/register",{
        method : "POST",
        credentials : "include",
        headers : {
          "Content-Type" : "application/json",
        },
        body : JSON.stringify(userDetails),
      })
      // if(res.ok){
      const data = await res.json();
      console.log(data);
        
      // }
      toast({
        variant : res.ok ? "default" : "destructive",
        title : res.ok ? "Added User Successfully" : "Unexpected Error Occured"
      
      });
      
    } catch (error) {
      console.log("Error! :" + error);
      toast({
        variant : "destructive",
        title : "Internal Server Error occured"
      
      });
    }
  }

  useEffect(() =>{
    const fetchUsers = async () =>{
      try {
        const res = await fetch("http://localhost:8080/admin/user",{
          credentials : "include"
        })
        const data = await res.json();
        setAllUsers(data);
      } catch (error) {
        console.log("ERROR: " + error);
      }
    };

    fetchUsers();
  }, []);

  return (
    <section className="container mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">Admin Panel - Users</h1>

      {/* Create Users */}
      <div className="flex flex-col gap-2 [&>*]:bg-white border-4 rounded-md my-2">
        <Input 
          type="text" 
          placeholder="Email" 
          // className = "w-1/2"
          // value={email}
          onChange={(e) => { setEmail(e.target.value)}}
          required
        />
        <Input 
          type="text" 
          placeholder="Password" 
          // className = "w-1/2"
          // value={password}
          onChange={(e) => { setPassword(e.target.value)}}
          required
        />        
        <Input 
          type="text" 
          placeholder="Role" 
          // className = "w-1/2"
          // value={role}
          onChange={(e) => { setRole(e.target.value)}}
          required
        />      </div>
      <Button className="mb-4" onClick={handleCreateUser}> Create User</Button>
      {/* <Button className="mb-4"> Update User</Button> */}


      <div className="overflow-x-auto">
        <table className="min-w-full bg-white border border-gray-200 rounded-lg">
          <thead>
            <tr className="bg-gray-100 border-b">
              {/* <th className="px-4 py-2 text-left">Name</th> */}
              <th className="px-4 py-2 text-left">Email</th>
              <th className="px-4 py-2 text-left">Role</th>
            </tr>
          </thead>
          <tbody>
            {users.length > 0 ? (
              users.map((user) => (
                <tr key={user.email} className="border-b hover:bg-gray-50">
                  {/* <td className="px-4 py-2">{user.username}</td> */}
                  <td className="px-4 py-2">{user.email}</td>
                  <td className="px-4 py-2">{user.role}</td>

                </tr>
              ))
            ) : (
              <tr>
                <td className="px-4 py-2" colSpan="2">No users found.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

    </section>
  )
}

export default AdminUserPage