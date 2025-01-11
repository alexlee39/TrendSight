/** @type {import('tailwindcss').Config} */
export default {
  // Add default html styling
//   corePlugins: {
//     preflight: false,
//  },
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage:{
        'custom-background': "url('/src/assets/cool-background.png')",
      },
      spacing: {
        '100' : '25rem',
        '110' : '27.5rem',
      },
      colors: {
        'off-white': '#f5f5f5',
      },
      animation: {
        fadeIn: 'fadeIn 0.5s ease height 0.2s ease',
      },
      keyframes: {
        fadeIn: {
          from: { 
            opacity: '0',
            transform: 'scale(0.9)',
          },
          to: {
            opacity: '1',
            transform: 'scale(1)',
          }, 
        },
      },
    },
  },
  plugins: [],
}