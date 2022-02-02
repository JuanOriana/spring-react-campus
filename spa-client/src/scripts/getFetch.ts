import { ErrorResponse, Result } from "../types";
import { authedFetch } from "./authedFetch";
import { checkError } from "./ErrorChecker";

export async function getFetch<RetType>(url: string): Promise<Result<RetType>> {
  try {
    const response = await authedFetch(url, {
      method: "GET",
    });
    const parsedResponse = await checkError<RetType>(response);
    return Result.ok(parsedResponse as RetType);
  } catch (err: any) {
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
