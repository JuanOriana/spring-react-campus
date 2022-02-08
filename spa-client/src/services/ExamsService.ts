import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { ExamModel, PagedContent, PostResponse, Result } from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { authedFetch } from "../scripts/authedFetch";
import { postFetch } from "../scripts/postFetch";

export class ExamsServices {
  private readonly basePath = paths.BASE_URL + paths.EXAMS;
  public async getExamById(examId: number): Promise<Result<ExamModel>> {
    return getFetch<ExamModel>(this.basePath + "/" + examId);
  }

  public async getExamAnswers(
    examId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    return getPagedFetch<AnswerModel[]>(
      this.basePath + "/" + examId + "/answers"
    );
  }

  public async deleteExam(examId: number) {
    return authedFetch(this.basePath + "/" + examId, { method: "DELETE" });
  }

  public async newAnswer(
    examId: number,
    answerFile: File
  ): Promise<Result<PostResponse>> {
    const newAnswer = JSON.stringify({
      exam: answerFile,
    });

    return postFetch<PostResponse>(
      this.basePath + "/" + examId + "/answers",
      "application/vnd.campus.api.v1+json",
      newAnswer
    );
  }
}
