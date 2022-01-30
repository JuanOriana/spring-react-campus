import CourseModel from "./CourseModel";
import UserModel from "./UserModel";

export default interface AnnouncementModel {
  announcementId: number;
  title: string;
  contetn: string;
  author: UserModel;
  time: Date;
  course: CourseModel;
}
