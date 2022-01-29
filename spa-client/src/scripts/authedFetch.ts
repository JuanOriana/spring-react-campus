function updateOptions(options: any) {
  const update = { ...options };
  if (localStorage.jwt) {
    update.headers = {
      ...update.headers,
      Authorization: `Bearer ${localStorage.jwt}`,
    };
  }
  return update;
}

export function authedFetch(url: string, options: any) {
  return fetch(url, updateOptions(options));
}
