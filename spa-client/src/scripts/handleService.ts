import { NavigateFunction } from "react-router-dom";
import { Result } from "../types";

export function handleService<T>(
  promise: Promise<Result<T>>,
  navigate: NavigateFunction,
  setterFunction: (data: T) => void,
  cleanerFunction: () => void
): void {
  promise
    .then((response: Result<T>) => {
      if (response.hasFailed()) {
        navigate(`/error?code=${response.getError().getCode()}`);
      } else {
        setterFunction(response.getData());
      }
    })
    .catch(() => navigate("/error?code=500"))
    .finally(cleanerFunction);
}
