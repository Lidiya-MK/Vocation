function showToast(message, type = "info") {
  if (!document.getElementById("toast-container")) {
    const toastContainer = document.createElement("div");
    toastContainer.id = "toast-container";

    toastContainer.style.position = "fixed";
    toastContainer.style.top = "20px";
    toastContainer.style.right = "20px";
    toastContainer.style.zIndex = "1000";

    document.body.appendChild(toastContainer);
  }

  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.textContent = message;

  toast.style.backgroundColor =
    type === "success" ? "#4CAF50" : type === "error" ? "#f44336" : "#2196F3";
  toast.style.color = "#fff";
  toast.style.padding = "15px 25px";
  toast.style.marginBottom = "10px";
  toast.style.borderRadius = "8px";
  toast.style.boxShadow = "0 4px 6px rgba(0, 0, 0, 0.2)";
  toast.style.fontSize = "16px";
  toast.style.animation = "fadeInOut 3s";

  document.getElementById("toast-container").appendChild(toast);

  setTimeout(() => {
    toast.remove();
  }, 3000);
}

const styleSheet = document.createElement("style");
styleSheet.textContent = `
  @keyframes fadeInOut {
    0% { opacity: 0; transform: translateY(-20px); } /* Move from above */
    10% { opacity: 1; transform: translateY(0); }
    90% { opacity: 1; transform: translateY(0); }
    100% { opacity: 0; transform: translateY(-20px); } /* Fade out to above */
  }
  .toast {
    animation: fadeInOut 3s ease-in-out;
  }
`;
document.head.appendChild(styleSheet);
