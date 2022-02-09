import { PostResponse } from "../types";

export function postErrorChecker(response: Response): PostResponse {
  if (
    response.status >= 200 &&
    response.status <= 299 &&
    response.status !== 204
  ) {
    return { url: response.headers.get("Location") } as PostResponse;
  } else {
    throw Error(response.status.toString());
  }
}
