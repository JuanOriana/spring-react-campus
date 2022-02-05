import { pageUrlMaker } from "./pageUrlMaker";

export function fileUrlMaker(
  basePath: string,
  categoryType?: number[],
  extensionType?: number[],
  query?: string,
  orderProperty?: string,
  orderDirection?: string,
  page?: number,
  pageSize?: number
): URL {
  let url = pageUrlMaker(basePath, page, pageSize);

  if (typeof categoryType !== "undefined") {
    url.searchParams.append("categoryType", categoryType.toLocaleString());
  }
  if (typeof extensionType !== "undefined") {
    url.searchParams.append("extensionType", extensionType.toLocaleString());
  }
  if (typeof query !== "undefined") {
    url.searchParams.append("query", query);
  }
  if (typeof orderProperty !== "undefined") {
    url.searchParams.append("orderProperty", orderProperty);
  }
  if (typeof orderDirection !== "undefined") {
    url.searchParams.append("orderDirection", orderDirection);
  }
  return url;
}
