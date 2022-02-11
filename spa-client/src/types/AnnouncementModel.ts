import CourseModel from "./CourseModel";
import UserModel from "./UserModel";

export default interface AnnouncementModel {
  announcementId: number;
  title: string;
  content: string;
  author: UserModel;
  date: string;
  course: CourseModel;
}
