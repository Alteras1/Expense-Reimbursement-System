asyncFetch("/logout", (success) => {
  let output = document.getElementById("output");
  localStorage.clear();
  if (success) {
    output.innerText = "Successfully Logged out. Returning to Main";
  } else {
    output.innerText = "Log out failed/No User found Returning to Main";
  }
  setTimeout(function () {
    window.location.href = "/Reimbursement/";
  }, 2000);
});
