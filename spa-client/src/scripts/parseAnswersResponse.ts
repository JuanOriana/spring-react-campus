import { PagedContent, Result } from "../types";
import AnswerModel from "../types/AnswerModel";

export function parseAnswersResponse(
  answers: Result<PagedContent<AnswerModel[]>>
): Result<PagedContent<AnswerModel[]>> {
  if (!answers.hasFailed()) {
    answers
      .getData()
      .getContent()
      .forEach(
        (answer) =>
          (answer.deliveredDate = answer.deliveredDate
            ? new Date(answer.deliveredDate)
            : undefined)
      );
  }

  return answers;
}
