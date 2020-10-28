async function asyncFetch(
  url,
  expression,
  body = {
    method: "GET",
  }
) {
  const response = await fetch(api + url, body);
  const json = await response.json();
  console.log(json);
  expression(json);
}

var api = "/Reimbursement/api";
