function addToken(token) {
  document.cookie = `accessToken=${token}; path=/;`;
  localStorage.setItem("accessToken", token);
}

function getToken() {
  return localStorage.getItem("accessToken");
}

function removeToken() {
  const cookieName = "accessToken";

  localStorage.removeItem(cookieName);
  document.cookie = `${cookieName}=; path=/; Expires=Thu, 01 Jan 1970 00:00:00 GMT; SameSite=Strict;`;
}
