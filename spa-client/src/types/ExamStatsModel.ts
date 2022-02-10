import AnswerModel from "./AnswerModel";
import { ExamModel } from "./index";

export default interface ExamStatsModel {
  average: number;
  corrected: AnswerModel[];
  notCorrected: AnswerModel[];
  exam: ExamModel;
}
