import { paths } from "../common/constants";
import { ExamModel, PagedContent, PostResponse, Result } from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { authedFetch } from "../scripts/authedFetch";
import { resultFetch } from "../scripts/resultFetch";
import { parseAnswersResponse } from "../scripts/parseAnswersResponse";

export class ExamsServices {
  private readonly basePath = paths.BASE_URL + paths.EXAMS;
  public async getExamById(examId: number): Promise<Result<ExamModel>> {
    return resultFetch<ExamModel>(this.basePath + "/" + examId, {
      method: "GET",
    });
  }

  public async getExamAnswers(
    examId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    const resp = await getPagedFetch<AnswerModel[]>(
      this.basePath + "/" + examId + "/answers"
    );

    return parseAnswersResponse(resp);
  }

  public async deleteExam(examId: number) {
    return authedFetch(this.basePath + "/" + examId, { method: "DELETE" });
  }

  public async newAnswer(
    examId: number,
    answerFile: File
  ): Promise<Result<PostResponse>> {
    const newAnswer = new FormData();
    newAnswer.append("file", answerFile, answerFile.name);

    return resultFetch<PostResponse>(
      this.basePath + "/" + examId + "/answers",
      {
        method: "POST",
        hearders: {},
        body: newAnswer,
      }
    );
  }
}
