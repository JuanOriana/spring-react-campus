import { paths } from "../common/constants";
import { authedFetch } from "../scripts/authedFetch";
import { checkError } from "../scripts/ErrorChecker";
import { getFetch } from "../scripts/getFetch";
import { ErrorResponse, ExamModel, Result } from "../types";
import AnswerModel from "../types/AnswerModel";

export class ExamsServices {
  private readonly basePath = paths.BASE_URL + paths.EXAMS;
  public async getExamById(examId: number): Promise<Result<ExamModel>> {
    return getFetch<ExamModel>(this.basePath + examId);
  }

  public async getExamAnswers(examId: number): Promise<Result<AnswerModel[]>> {
    return getFetch<AnswerModel[]>(this.basePath + examId + "/answers");
  }
}
