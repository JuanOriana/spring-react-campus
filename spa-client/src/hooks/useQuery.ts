import { useLocation } from "react-router-dom";
import React from "react";

export function useQuery() {
  const { search } = useLocation();
  return React.useMemo(() => new URLSearchParams(search), [search]);
}

export function getQueryOrDefault(
  query: URLSearchParams,
  queryParam: string,
  defaultRet: string
) {
  const fetcher = query.get(queryParam);
  if (fetcher === null) {
    return defaultRet;
  }
  return fetcher;
}
