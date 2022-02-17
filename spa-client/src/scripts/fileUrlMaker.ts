export function fileUrlMaker(
  basePath: string,
  categoryType?: number[],
  extensionType?: number[],
  query?: string,
  orderProperty?: string,
  orderDirection?: string
): URL {
  let url = new URL(basePath);
  if (categoryType && categoryType.length > 0) {
    url.searchParams.append("category-type", categoryType.toLocaleString());
  }
  if (extensionType && extensionType.length > 0) {
    url.searchParams.append("extension-type", extensionType.toLocaleString());
  }
  if (query && query !== "") {
    url.searchParams.append("query", query);
  }
  if (orderProperty && orderProperty !== "") {
    url.searchParams.append("order-property", orderProperty);
  }
  if (orderDirection && orderDirection !== "") {
    url.searchParams.append("order-direction", orderDirection);
  }
  return url;
}
