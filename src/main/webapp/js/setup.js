asyncFetch("/user", currentUser);

function currentUser(json) {
  localStorage.setItem("user", JSON.stringify(json));
  let user = json;
  document.getElementById("currentUser").innerText =
    user.firstName + " " + user.lastName;
  renderReimb();
}

asyncFetch("/status", statuses);
asyncFetch("/type", types);

var reimbStatus;
var reimbType;
function statuses(json) {
  reimbStatus = json;
  let select = Array.from(document.getElementsByClassName("status"));
  for (let obj in reimbStatus) {
    let opt = document.createElement("option");
    opt.value = obj.typeId;
    opt.innerHTML = obj.type;
    select.forEach((node) => node.appendChild(opt));
  }
}

function types(json) {
  reimbType = json;
  let select = Array.from(document.getElementsByClassName("type"));
  for (let obj of reimbType) {
    let opt = document.createElement("option");
    opt.value = obj.typeId;
    opt.innerHTML = obj.type;
    select.forEach((node) => node.appendChild(opt));
  }
}
