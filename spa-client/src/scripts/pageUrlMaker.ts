export function pageUrlMaker(
  path: string,
  page?: number,
  pageSize?: number
): URL {
  let url = new URL(path);
  if (typeof page !== "undefined") {
    url.searchParams.append("page", page.toString());
  }

  if (typeof pageSize !== "undefined") {
    url.searchParams.append("pageSize", pageSize.toString());
  }

  return url;
}
