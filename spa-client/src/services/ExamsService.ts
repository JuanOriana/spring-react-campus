import { paths } from "../common/constants";
import { getFetch } from "../scripts/getFetch";
import { ExamModel, PagedContent, Result } from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";

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
}
