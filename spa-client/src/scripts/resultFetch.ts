import { ErrorResponse, Result } from "../types";
import { authedFetch } from "./authedFetch";
import { checkError } from "./ErrorChecker";
import { postErrorChecker } from "./postErrorChecker";

export async function resultFetch<RetType>(
  url: string,
  options: any
): Promise<Result<RetType>> {
  try {
    let parsedResponse;

    const response = await authedFetch(url, options);

    if (options.method === "POST") {
      parsedResponse = postErrorChecker(response);
    } else {
      parsedResponse = await checkError<RetType>(response);
    }
    return Result.ok(parsedResponse as RetType);
  } catch (err: any) {
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
