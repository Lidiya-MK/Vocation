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

async function sendCreateRequest(url, payload) {
  try {
    const response = await fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + getToken(),
      },
      body: JSON.stringify(payload),
    });

    if (response.ok) {
      closeModal();
      document.getElementById("selectedSkillsContainer").innerHTML = "";
      showToast("Job posted successfully!", "success");
      setTimeout(() => {
        window.location.reload();
      }, 1500);
      return;
    }

    if (response.status === 400) {
      const errorData = await response.json();
      let message =
        errorData.title ||
        errorData.type ||
        errorData.budget ||
        errorData.startDate ||
        errorData.description ||
        errorData.applicationDeadline ||
        errorData.skillsRequired ||
        errorData.error ||
        "Invalid job data. Please check your inputs";

      if (
        message.includes("java") ||
        message.includes("Java") ||
        message.includes("SQL") ||
        message.includes("Spring")
      ) {
        message = "Invalid job data. Please check your inputs";
      }

      showToast(message, "error");
      return;
    }

    showToast("Failed to post job. Please try again later1", "error");
  } catch (error) {
    showToast("Failed to post job. Please try again later2", "error");
  }
}
