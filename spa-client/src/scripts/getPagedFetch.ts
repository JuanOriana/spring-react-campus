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
    const parsedResponse = await checkError<RetType>(response);
    response.headers.forEach((header) => console.log(header));
    const maxPage = response.headers
      .get("X-Total-Pages")
      ?.toString()
      .split(" ")[1];
    console.log(maxPage);
    return Result.ok(
      new PagedContent(parsedResponse, maxPage ? parseInt(maxPage) : 1)
    );
  } catch (err: any) {
    return Result.failed(new ErrorResponse(parseInt(err.message), err.message));
  }
}
