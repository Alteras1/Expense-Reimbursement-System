function reimbRenderer() {
  let user = JSON.parse(localStorage.getItem("user"));
  filtering = false;
  asyncFetch(`/reimb`, renderReimb);
}

var masterData;

function renderReimb(data) {
  data = data.map((reimb) => postProcessReimb(reimb));
  data.sort((a, b) => (a.reimbId > b.reimbId ? -1 : 1)); //display latest to oldest
  if (!filtering) {
    masterData = data;
  }
  const table = document.getElementById("reimbRows");
  table.innerHTML = "";
  for (let reimb of data) {
    reimb.resolved;
    rowBuilder(
      table,
      reimb.reimbId,
      `${reimb.author.firstName} ${reimb.author.lastName}`,
      `$${reimb.amount}`,
      reimb.description,
      reimb.submitted.toDateString(),
      reimb.type.type,
      reimb.status.status,
      reimb.resolved ? reimb.resolved.toDateString() : "-",
      reimb.resolver
        ? `${reimb.resolver.firstName} ${reimb.resolver.lastName}`
        : "-"
    );
  }
}

const account = document.getElementById("account");

account.addEventListener("submit", (event) => {
  event.preventDefault();
  let data = formToJson(event);
  data.role = [
    { role: "Employee", roleId: 1 },
    { role: "Manager", roleId: 2 },
  ][data.role - 1];
  console.log(data);
  document.getElementById("createStatus").innerHTML =
    '<i class="fas fa-spinner fa-spin fa-lg fa-fw"></i>';
  asyncFetch(
    "/user/new",
    (json) => {
      if (json == true) {
        document.getElementById("createStatus").innerHTML =
          '<i class="fas fa-check-circle fa-lg fa-fw"></i> Creation successful';
      } else {
        document.getElementById("createStatus").innerHTML =
          '<i class="fas fa-times-circle fa-lg fa-fw"></i> Creation failed';
      }
    },
    { method: "POST", body: JSON.stringify(data) }
  );
});

const filter = document.getElementById("filter");
var filtering = false;

filter.addEventListener("submit", (event) => {
  event.preventDefault();
  filterOption = formToJson(event);
  filtering = true;
  let data = masterData.filter(
    (a) =>
      (filterOption.filterStatus != 0
        ? a.status.statusId == filterOption.filterStatus
        : true) &&
      (filterOption.filterType != 0
        ? a.type.typeId == filterOption.filterType
        : true)
  );
  renderReimb(data);
});

var claimingReimb;

document.getElementById("viewClaim").addEventListener("submit", (event) => {
  event.preventDefault();
  let id = document.getElementById("reimbId").value;
  console.log(id);
  data = masterData.filter((a) => a.reimbId == id);
  if (data.length == 1) {
    claimingReimb = data[0];
    document.getElementById("claimId").innerHTML = claimingReimb.reimbId;
    document.getElementById("claimAuthor").innerHTML =
      claimingReimb.author.firstName + " " + claimingReimb.author.lastName;
    document.getElementById("claimAmount").innerHTML = claimingReimb.amount;
    document.getElementById("claimDescription").innerText =
      claimingReimb.description;
    document.getElementById(
      "claimSubmitted"
    ).innerHTML = claimingReimb.submitted.toDateString();
    document.getElementById("claimStatus").innerHTML =
      claimingReimb.status.status;
    if (claimingReimb.status.statusId != 1) {
      document.getElementById("updateSubmit").disable = true;
    } else {
      document.getElementById("updateSubmit").disable = false;
    }
  }
});

document.getElementById("updateSubmit").addEventListener("click", (event) => {
  event.preventDefault();
  document.getElementById("updateStatus").innerHTML =
    '<i class="fas fa-spinner fa-spin fa-lg fa-fw"></i>';
  let status = reimbStatus.filter(
    (a) => (a.statusId = document.getElementById("claimUpdate").value)
  )[0];
  asyncFetch("/reimbVerify", reimbRenderer, {
    method: "POST",
    body: JSON.stringify({ reimb: claimingReimb, status: status }),
  });
  document.getElementById("updateStatus").innerHTML =
    '<i class="fas fa-check-circle fa-lg fa-fw"></i>';
});
