import { ErrorResponse, Result } from "../types";
import { authedFetch } from "./authedFetch";
import { checkError } from "./ErrorChecker";

export async function resultFetch<RetType>(
  url: string,
  options: any
): Promise<Result<RetType>> {
  try {
    const response = await authedFetch(url, options);
    const parsedResponse = await checkError<RetType>(response);

    return Result.ok(parsedResponse as RetType);
  } catch (err: any) {
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
