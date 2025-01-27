function createLoginHandler(role, redirectUrl) {
  return async function handleLogin(event) {
    event.preventDefault();

    const form = event.target;
    const email = form.querySelector('input[name="email"]').value;
    const password = form.querySelector('input[name="password"]').value;

    const payload = {
      email: email,
      password: password,
      role: role,
    };

    try {
      const response = await fetch("/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        const data = await response.json();
        addToken(data.token);
        window.location.href = redirectUrl;
      } else if (response.status === 400) {
        const error = await response.json();
        showToast(
          error.email ||
            error.password ||
            error.error ||
            "An error occurred. Please try again.",
          "error"
        );
      } else if (response.status === 401 || response.status === 403) {
        const error = await response.json();
        showToast("Invalid credentials. Please try again.", "error");
      } else {
        showToast("An error occurred. Please try again.", "error");
      }
    } catch (error) {
      showToast(error, "error");
    }
  };
}
