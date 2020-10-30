const newReimb = document.getElementById("addReimb");

newReimb.addEventListener("submit", (event) => {
  event.preventDefault();
  let data = formToJson(event);
  data.status = reimbStatus[0];
  data.type = reimbType[data.type];
  data.author = JSON.parse(localStorage.getItem("user"));
  document.getElementById("newReimbStatus").innerHTML =
    '<i class="fas fa-spinner fa-spin fa-lg fa-fw"></i>';
  asyncFetch("/reimb", reimbRenderer, {
    method: "POST",
    body: JSON.stringify(data),
  });
});

function reimbRenderer() {
  if (document.getElementById("newReimbStatus").innerHTML.length != 0) {
    document.getElementById("newReimbStatus").innerHTML =
      '<i class="fas fa-check-circle fa-lg fa-fw"></i>';
  }
  let user = JSON.parse(localStorage.getItem("user"));
  asyncFetch(`/reimb/author/${user.userId}`, renderReimb);
}

function renderReimb(data) {
  data = data.map((reimb) => postProcessReimb(reimb));
  data.sort((a, b) => (a.reimbId > b.reimbId ? -1 : 1)); //display latest to oldest
  const table = document.getElementById("reimbRows");
  table.innerHTML = "";
  for (let reimb of data) {
    reimb.resolved;
    rowBuilder(
      table,
      reimb.reimbId,
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

function getAccount() {
  let user = JSON.parse(localStorage.getItem("user"));
  const account = document.getElementById("account");
  populate(account, user);
}

const account = document.getElementById("account");

account.addEventListener("submit", (event) => {
  event.preventDefault();
  let user = JSON.parse(localStorage.getItem("user"));
  let data = formToJson(event);
  user.firstName =
    user.firstName != data.firstName ? data.firstName : user.firstName;
  user.lastName =
    user.lastName != data.lastName ? data.lastName : data.lastName;
  user.email = user.email != data.email ? data.email : user.email;
  user.username =
    user.username != data.username ? data.username : user.username;
  user.password = data.oldPassword;
  console.log(user);
  if (data.oldPassword != data.newPassword && data.newPassword.length > 0) {
    user.password = data.newPassword;
  }
  if (data.oldPassword != null) {
    document.getElementById("updateStatus").innerHTML =
      '<i class="fas fa-spinner fa-spin fa-lg fa-fw"></i>';
    asyncFetch(
      "/user",
      (json) => {
        if (json == true) {
          window.location.href = "/Reimbursement/logout.html";
        } else {
          document.getElementById("updateStatus").innerHTML =
            '<i class="fas fa-times-circle fa-lg fa-fw"></i> Update Failed';
        }
      },
      { method: "POST", body: JSON.stringify(user) }
    );
  }
});

// const newUser = document.getElementById("username");

// newUser.addEventListener("change", usernameChecker());

// function usernameChecker() {
//   console.log(true);
//   let user = JSON.parse(localStorage.getItem("user"));
//   if (newUser.innerText != user.username) {
//     document.getElementById("updateSubmit").disabled = true;
//     asyncFetch(
//       "/user/avail" + new URLSearchParams({ username: data.username }),
//       (json) => {
//         if (json == true) {
//           document.getElementById("updateSubmit").disabled = false;
//           document.getElementById("avail").innerHTML = "Username Available";
//         } else {
//           document.getElementById("avail").innerHTML = "Username Unavailable";
//         }
//       }
//     );
//   }
// }
