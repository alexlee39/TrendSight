import React from 'react'

const Background = ({children}) => {
  return (
    <div className=" flex justify-center items-center min-h-screen bg-custom-background bg-no-repeat bg-cover bg-center">
        {children}
    </div>
  )
}

export default Background