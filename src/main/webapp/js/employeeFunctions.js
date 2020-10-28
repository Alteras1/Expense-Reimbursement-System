function reimbRenderer() {
  let user = JSON.parse(localStorage.getItem("user"));
  asyncFetch(`/reimb/author/${user.reimbId}`, renderReimb);
}

function renderReimb(data) {
  table = document.getElementById("reimbRows");
  for (let reimb of data) {
    let row = table.insertRow(-1);
    Object.values(reimb).forEach((prop) => {
      let cell = row.insertCell(-1);
      let text = document.createTextNode(prop);
      cell.appendChild(text);
    });
  }
}
