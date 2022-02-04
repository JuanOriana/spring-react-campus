import { authedFetch } from "./authedFetch";

export async function postFetch(
  path: string,
  contentType: string,
  body: string
) {
  const headers = new Headers();
  headers.append("Content-Type", contentType);

  return authedFetch(path, {
    method: "POST",
    headers: headers,
    body: body,
  });
}
