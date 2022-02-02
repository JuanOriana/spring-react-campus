function updateOptions(options: any) {
  const update = { ...options };
  const token = localStorage.getItem("token");
  if (token) {
    const headers = update.headers as Headers;
    headers.append("Authorization", `Bearer ${token}`);
  }
  return update;
}

export function authedFetch(url: string, options: any) {
  return fetch(url, updateOptions(options));
}
