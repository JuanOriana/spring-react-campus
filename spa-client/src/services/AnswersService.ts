import { paths } from "../common/constants";
import { authedFetch } from "../scripts/authedFetch";
import { resultFetch } from "../scripts/resultFetch";
import { PutResponse, Result } from "../types";
import AnswerModel from "../types/AnswerModel";

export class AnswersService {
  private readonly basePath = paths.BASE_URL + paths.ANSWERS;

  public async getAnswerById(answerId: number): Promise<Result<AnswerModel>> {
    const resp = await resultFetch<AnswerModel>(
      this.basePath + "/" + answerId,
      {
        method: "GET",
      }
    );

    if (resp.hasFailed()) return resp;

    resp.getData().deliveredDate = new Date(resp.getData().deliveredDate);

    return resp;
  }

  public async deleteAnswer(answerId: number) {
    return authedFetch(this.basePath + "/" + answerId, {
      method: "DELETE",
    });
  }

  public async correctAnswer(
    answerId: number,
    correction?: string,
    score?: number
  ): Promise<Result<PutResponse>> {
    const corrections = JSON.stringify({
      correction: correction,
      score: score,
    });
    return resultFetch<PutResponse>(this.basePath + "/" + answerId, {
      method: "PUT",
      headers: { "Content-Type": "application/vnd.campus.api.v1+json" },
      body: corrections,
    });
  }
}
