import { ExamModel, PagedContent, Result } from "../types";

export function parseExamModelResponse(
  exams: Result<PagedContent<ExamModel[]>>
): Result<PagedContent<ExamModel[]>> {
  if (!exams.hasFailed()) {
    exams
      .getData()
      .getContent()
      .forEach((exam) => {
        console.log(exam.endTime);

        exam.endTime = exam.endTime ? new Date(exam.endTime) : undefined;
        exam.startTime = exam.startTime ? new Date(exam.startTime) : undefined;
      });
  }

  return exams;
}
