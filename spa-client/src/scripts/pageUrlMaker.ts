export function pageUrlMaker(
  path: string,
  page?: number,
  pageSize?: number
): URL {
  let url = new URL(path);
  if (page) {
    url.searchParams.append("page", page.toString());
  }

  if (pageSize) {
    url.searchParams.append("page-size", pageSize.toString());
  }

  return url;
}
