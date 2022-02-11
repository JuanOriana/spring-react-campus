import CourseModel from "./CourseModel";
import FileModel from "./FileModel";

export default interface ExamModel {
  examId: number;
  course: CourseModel;
  startTime?: string;
  endTime?: string;
  title: string;
  description: string;
  examFile?: FileModel;
  average: number;
  url: string;
}
