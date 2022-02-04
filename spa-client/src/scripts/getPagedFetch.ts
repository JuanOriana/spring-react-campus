import { ErrorResponse, PagedContent, Result } from "../types";
import { authedFetch } from "./authedFetch";
import { checkError } from "./ErrorChecker";

export async function getPagedFetch<RetType>(
  url: string
): Promise<Result<PagedContent<RetType>>> {
  try {
    const response = await authedFetch(url, {
      method: "GET",
    });
    console.log("OK: FETCH");

    const parsedResponse = await checkError<RetType>(response);
    const maxPage = response.headers.get("x-total-pages");
    return Result.ok(
      new PagedContent(parsedResponse, maxPage ? parseInt(maxPage) : 1)
    );
  } catch (err: any) {
    console.log("ERR: FETCH" + err.message);
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
