import { getQueryOrDefault, useQuery } from "./useQuery";

export function usePagination(defaultSize: number) {
  const query = useQuery();
  const currentPage = parseInt(getQueryOrDefault(query, "page", "1"));
  const pageSize = parseInt(
    getQueryOrDefault(query, "pageSize", defaultSize.toString())
  );

  return [currentPage, pageSize];
}
