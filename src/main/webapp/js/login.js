let json = localStorage.getItem("user");
if (json.length != 0) {
  let user = JSON.parse(json);
  document.getElementById(
    "accountLinks"
  ).innerText = `Welcome ${user.firstName} ${user.lastName}`;
  document.getElementById("loginButton").hidden = true;
  document.getElementById("logout").hidden = false;
  document.getElementById("employee").hidden = false;
  if (user.role.role == "Manager") {
    document.getElementById("manager").hidden = false;
  }
}
