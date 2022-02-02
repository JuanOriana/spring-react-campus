function updateOptions(options: any) {
  const update = { ...options };
  if (localStorage.jwt) {
    const headers = update.headers as Headers;
    headers.append("Authorization", `Bearer ${localStorage.jwt}`);
  }
  return update;
}

export function authedFetch(url: string, options: any) {
  return fetch(url, updateOptions(options));
}
