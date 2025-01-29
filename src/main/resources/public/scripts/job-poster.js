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
      }, 1000);
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

    showToast("Failed to post job. Please try again later", "error");
  } catch (error) {
    showToast("Failed to post job. Please try again later", "error");
  }
}

async function sendProfileUpdateRequest(url, payload) {
  try {
    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + getToken(),
      },
      body: JSON.stringify(payload),
    });

    if (response.ok) {
      showToast("Profile updated successfully!", "success");
      return;
    }

    if (response.status === 400) {
      const errorData = await response.json();
      let message =
        errorData.name ||
        errorData.email ||
        errorData.industry ||
        errorData.description ||
        errorData.location ||
        errorData.phoneNumber ||
        errorData.password ||
        errorData.error ||
        "Invalid profile data. Please check your inputs.";

      if (
        message.includes("java") ||
        message.includes("Java") ||
        message.includes("SQL") ||
        message.includes("Spring")
      ) {
        message = "Invalid profile data. Please check your inputs.";
      }

      showToast(message, "error");
      return;
    }

    showToast("Failed to update profile. Please try again later", "error");
  } catch (error) {
    showToast("Failed to update profile. Please try again later", "error");
  }
}

async function sendApplicationStatusUpdateRequest(
  jobId,
  applicationId,
  newStatus
) {
  try {
    const response = await fetch(
      `/job-posters/jobs/${jobId}/applications/${applicationId}`,
      {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + getToken(),
        },
        body: JSON.stringify({ newStatus }),
      }
    );

    if (response.ok) {
      showToast(
        `Application ${newStatus.toLowerCase()} successfully`,
        "success"
      );

      setTimeout(() => {
        window.location.reload();
      }, 1000);
    } else {
      showToast("Failed to update application status", "error");
    }
  } catch (error) {
    showToast("An error occurred. Please try again", "error");
  }
}
