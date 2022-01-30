import SubjectModel from "./SubjectModel";

export default interface CourseModel {
  courseId: number;
  year: number;
  quarter: number;
  board: string;
  subject: SubjectModel;
  courseUrl: string;
  isTeacher: boolean;
}
