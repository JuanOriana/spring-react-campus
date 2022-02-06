export function checkError<RetType>(response: Response): Promise<RetType> {
  if (
    response.status >= 200 &&
    response.status <= 299 &&
    response.status !== 204
  ) {
    return response.json() as Promise<RetType>;
  } else {
    throw Error(response.status.toString());
  }
}

export function checkBlobError(response: Response): Promise<Blob> {
  if (response.status >= 200 && response.status <= 299) {
    return response.blob() as Promise<Blob>;
  } else {
    throw Error(response.status.toString());
  }
}
