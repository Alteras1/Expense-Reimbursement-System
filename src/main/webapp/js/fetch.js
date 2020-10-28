async function asyncFetch(
  url,
  body = {
    method: "GET",
  },
  expression
) {
  const response = await fetch(url, body);
  const json = await response.json();
  expression(json);
}
