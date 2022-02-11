import { FileModel, UserModel } from ".";

export default interface AnswerModel {
  answerId: number;
  examUrl: string;
  answerUrl: string;
  deliveredDate: Date;
  student: UserModel;
  teacher: UserModel;
  answerFile: FileModel;
  score: number;
  corrections: string;
}
