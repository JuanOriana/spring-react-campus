import Course from "./Course";
import FileModel from "./FileModel";

export default interface Exam{
    examId:number;
    course:Course;
    startTime:Date;
    endTime:Date;
    title:string;
    description:string;
    examFile:FileModel;
    average:number;
    url:string;
}