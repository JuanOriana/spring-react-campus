export function checkError<RetType>(response: Response): Promise<RetType> {
    if (response.status >= 200 && response.status <= 299) {
      return response.json() as Promise<RetType>;
    } else {
      throw Error(response.status.toString());
    }
  }