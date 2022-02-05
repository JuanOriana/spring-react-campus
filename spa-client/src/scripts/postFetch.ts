import { authedFetch } from "./authedFetch";

export async function postFetch(
  path: string,
  contentType: string,
  body: string
) {
  return authedFetch(path, {
    method: "POST",
    headers: { "Content-Type": contentType },
    body: body,
  });
}
