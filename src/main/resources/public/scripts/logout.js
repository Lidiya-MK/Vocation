document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("logout-button")
    .addEventListener("click", (event) => {
      event.preventDefault();
      removeToken();
      window.location.href = "/";
    });
});
