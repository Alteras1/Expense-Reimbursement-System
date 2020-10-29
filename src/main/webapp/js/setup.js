asyncFetch("/user", currentUser);

function currentUser(json) {
  localStorage.setItem("user", JSON.stringify(json));
  let user = json;
  document.getElementById("currentUser").innerText =
    user.firstName + " " + user.lastName;
  reimbRenderer();
  getAccount();
}

asyncFetch("/status", statuses);
asyncFetch("/type", types);

var reimbStatus;
var reimbType;
function statuses(json) {
  reimbStatus = json;
  reimbStatus.sort((a, b) => (a.statusId > b.statusId ? 1 : -1));
  let select = [...document.getElementsByClassName("status")];
  for (let obj of reimbStatus) {
    let opt = document.createElement("option");
    opt.value = obj.statusId;
    opt.innerHTML = obj.status;
    select.forEach((node) => {
      let opt = document.createElement("option");
      opt.value = obj.statusId;
      opt.innerHTML = obj.status;
      node.appendChild(opt);
    });
  }
}

function types(json) {
  reimbType = json;
  reimbType.sort((a, b) => (a.typeId > b.typeId ? 1 : -1));
  let select = [...document.getElementsByClassName("type")];
  for (let obj of reimbType) {
    select.forEach((node) => {
      let opt = document.createElement("option");
      opt.value = obj.typeId;
      opt.innerHTML = obj.type;
      node.appendChild(opt);
    });
  }
}

function rowBuilder(table, ...input) {
  let row = table.insertRow(-1);
  for (let data of input) {
    let cell = row.insertCell(-1);
    let text = document.createTextNode(data);
    cell.appendChild(text);
  }
}

function postProcessReimb(reimb) {
  reimb.resolved = reimb.resolved ? new Date(reimb.resolved) : null;
  reimb.submitted = reimb.submitted ? new Date(reimb.submitted) : null;
  return reimb;
}

function formToJson(event) {
  const formData = new FormData(event.target);
  return JSON.parse(JSON.stringify(Object.fromEntries(formData)));
}
