function showConfirmBox(message, buttonType = "success") {
  return new Promise((resolve) => {
    const colors = {
      success: {
        button: "bg-green-600 hover:bg-green-700",
        border: "border-green-600",
      },
      error: {
        button: "bg-red-600 hover:bg-red-700",
        border: "border-red-600",
      },
      info: {
        button: "bg-blue-600 hover:bg-blue-700",
        border: "border-blue-600",
      },
    };

    const buttonColor = colors[buttonType] || colors.success;

    const modal = document.createElement("div");
    modal.className =
      "fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50";

    modal.innerHTML = `
      <div class="bg-white p-6 rounded-lg shadow-lg w-96 text-center flex flex-col">
          <div class="w-full ${buttonColor.border} border-t-4"></div> <!-- Top border -->
          <div class="pt-4 pb-6"> <!-- Padding around content -->
              <p class="text-lg font-semibold text-gray-800">${message}</p>
              <div class="mt-6 flex justify-center gap-4">
                  <button id="confirm-yes" class="px-4 py-2 ${buttonColor.button} text-white rounded-lg hover:opacity-80 transition">Yes</button>
                  <button id="confirm-no" class="px-4 py-2 bg-gray-400 text-white rounded-lg hover:bg-gray-500 transition">No</button>
              </div>
          </div>
      </div>
  `;

    document.body.appendChild(modal);

    document.getElementById("confirm-yes").addEventListener("click", () => {
      resolve(true);
      modal.remove();
    });

    document.getElementById("confirm-no").addEventListener("click", () => {
      resolve(false);
      modal.remove();
    });
  });
}
