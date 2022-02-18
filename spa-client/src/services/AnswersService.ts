import { APPLICATION_V1_JSON_TYPE, paths } from "../common/constants";
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
    const ans = resp.getData().deliveredDate;
    resp.getData().deliveredDate = ans ? new Date(ans) : undefined;

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
      headers: { "Content-Type": APPLICATION_V1_JSON_TYPE },
      body: corrections,
    });
  }
}
