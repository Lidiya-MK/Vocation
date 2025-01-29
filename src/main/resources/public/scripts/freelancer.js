fetch("freelancers/jobs", {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + getToken(),
    },
  });
  