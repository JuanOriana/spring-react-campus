import { ErrorResponse, Result } from "../types";
import { authedFetch } from "./authedFetch";
import { checkError } from "./ErrorChecker";
// TODO: HACER UNA FUNCION PARA EVITAR REPETIR CODIGO ACA Y EN GETFETCH.TS
export async function postFetch<T>(
  path: string,
  contentType: string,
  body: string
): Promise<Result<T>> {
  try {
    const response = await authedFetch(path, {
      method: "POST",
      headers: { "Content-Type": contentType },
      body: body,
    });

    const parsedResponse = await checkError<T>(response);

    return Result.ok(parsedResponse as T);
  } catch (err: any) {
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
