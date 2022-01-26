import Subject from './Subject'

export default interface Course {
  courseId: number;
  year: number;
  quarter:number;
  board:string;
  subject:Subject;
  courseUrl:string;
  isTeacher: boolean;
}
