function formToJson(event) {
  const formData = new FormData(event.target);
  return JSON.parse(JSON.stringify(Object.fromEntries(formData)));
}

const newReimb = document.getElementById("addReimb");

newReimb.addEventListener("submit", (event) => {
  event.preventDefault();
  let data = formToJson(event);
  data.status = reimbStatus[1];
  data.type = reimbType[data.type];
  data.author = JSON.parse(localStorage.getItem("user"));
  console.log(data);
  asyncFetch("/reimb", () => null, { method: "POST" });
});
