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


async function sendWithdrawApplicationRequest(withdrawUrl) {
  try {
    const token = getToken();
    const response = await fetch(withdrawUrl, {
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
