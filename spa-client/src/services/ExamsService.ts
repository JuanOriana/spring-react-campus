import { paths } from "../common/constants";
import {
  ExamModel,
  PagedContent,
  PostResponse,
  PutResponse,
  Result,
} from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { authedFetch } from "../scripts/authedFetch";
import { resultFetch } from "../scripts/resultFetch";
import { parseAnswersResponse } from "../scripts/parseAnswersResponse";
import { pageUrlMaker } from "../scripts/pageUrlMaker";

export class ExamsServices {
  private readonly basePath = paths.BASE_URL + paths.EXAMS;
  public async getExamById(examId: number): Promise<Result<ExamModel>> {
    const resp = await resultFetch<ExamModel>(this.basePath + "/" + examId, {
      method: "GET",
    });

    if (!resp.hasFailed()) {
      const exam = resp.getData();
      exam.endTime = exam.endTime ? new Date(exam.endTime) : undefined;
      exam.startTime = exam.startTime ? new Date(exam.startTime) : undefined;
    }

    return resp;
  }

  public async getExamAnswers(
    examId: number,
    page?: number,
    pageSize?: number,
    filterBy?: string
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    const url = pageUrlMaker(
      this.basePath + "/" + examId + "/answers",
      page,
      pageSize
    );

    if (typeof filterBy !== "undefined") {
      url.searchParams.append("filter-by", filterBy);
    }

    const resp = await getPagedFetch<AnswerModel[]>(url.toString());

    return parseAnswersResponse(resp);
  }

  public async deleteExam(examId: number) {
    return authedFetch(this.basePath + "/" + examId, { method: "DELETE" });
  }

  public async editAnswerByStudent(
    examId: number,
    answerFile: File
  ): Promise<Result<PutResponse>> {
    const newAnswer = new FormData();
    newAnswer.append("file", answerFile, answerFile.name);

    return resultFetch<PutResponse>(this.basePath + "/" + examId + "/answers", {
      method: "PUT",
      hearders: {},
      body: newAnswer,
    });
  }
}
