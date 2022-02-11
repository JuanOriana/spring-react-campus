import { ExamModel, PagedContent, Result } from "../types";

export function parseExamModelResponse(
  exams: Result<PagedContent<ExamModel[]>>
): Result<PagedContent<ExamModel[]>> {
  if (!exams.hasFailed()) {
    exams
      .getData()
      .getContent()
      .forEach((exam) => {
        exam.endTime = new Date(exam.endTime ? exam.endTime : "");
        exam.startTime = new Date(exam.startTime ? exam.startTime : "");
      });
  }

  return exams;
}
