import React from 'react'
import { Link } from 'react-router'

const Navlink = ({tagName, path}) => {
  return (
    <Link to ={path} className="relative text-lg text-white font-medium my-2 mx-6 group">
    {tagName}
      {/* Bar that is displayed once hovered */}
      <span
        className="absolute -bottom-2 right-0 w-0 h-[3px] bg-white transition-all rounded-md duration-[400ms] group-hover:w-full group-hover:left-0"
      >
      </span>
    </Link>
  )
}

export default Navlink