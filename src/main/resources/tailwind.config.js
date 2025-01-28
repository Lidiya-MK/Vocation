/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./public/**/*.{html,js}",
    "./templates/**/*.{html,js}",
    "./templates/*.html",
  ],
  theme: {
    extend: {
      screens: {
        "sm-md": "930px",
      },
    },
  },
  plugins: [],
};
