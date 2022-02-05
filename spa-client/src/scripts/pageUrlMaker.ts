export function pageUrlMaker(
  path: string,
  page?: number,
  pageSize?: number
): URL {
  let url = new URL(path);
  let params = new URLSearchParams();
  let presents = false;
  if (typeof page !== "undefined") {
    presents = true;
    params.append("page", page.toString());
  }

  if (typeof pageSize !== "undefined") {
    presents = true;
    params.append("pageSize", pageSize.toString());
  }

  if (presents) {
    url.search = params.toString();
  }

  return url;
}
