import { paths } from "../common/constants";
import { authedFetch } from "../scripts/authedFetch";
import { getFetch } from "../scripts/getFetch";
import { Result } from "../types";
import AnswerModel from "../types/AnswerModel";

export class AnswersService {
  private readonly basePath = paths.BASE_URL + paths.ANSWERS;

  public async getAnswerById(answerId: number): Promise<Result<AnswerModel>> {
    return getFetch<AnswerModel>(this.basePath + "/" + answerId);
  }

  public async deleteAnswer(answerId: number) {
    return authedFetch(this.basePath + "/" + answerId, {
      method: "DELETE",
    });
  }
}
