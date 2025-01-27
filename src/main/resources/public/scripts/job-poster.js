async function sendDeleteJobRequest(deleteUrl) {
  try {
    const token = getToken();
    const response = await fetch(deleteUrl, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    });

    if (response.ok) {
      return true;
    } else {
      const error = await response.json();
      showToast(error.error, "error");
      return false;
    }
  } catch (error) {
    showToast(error.message, "error");
    return false;
  }
}
