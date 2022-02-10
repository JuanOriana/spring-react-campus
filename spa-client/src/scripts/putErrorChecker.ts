import { PutResponse } from "../types";

export function putErrorChecker(response: Response): PutResponse {
  if (
    response.status >= 200 &&
    response.status <= 299 &&
    response.status !== 204
  ) {
    return { statusCode: response.status } as PutResponse;
  } else {
    throw Error(response.status.toString());
  }
}
