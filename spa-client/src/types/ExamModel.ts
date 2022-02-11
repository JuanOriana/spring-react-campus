import CourseModel from "./CourseModel";
import FileModel from "./FileModel";

export default interface ExamModel {
  examId: number;
  course: CourseModel;
  startTime?: Date;
  endTime?: Date;
  title: string;
  description: string;
  examFile?: FileModel;
  average: number;
  url: string;
}
