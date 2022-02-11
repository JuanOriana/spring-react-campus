import { ExamModel } from ".";
import AnswerModel from "./AnswerModel";

export default interface SolvedExamModel {
  exam: ExamModel;
  answer: AnswerModel;
}
