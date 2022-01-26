import Course from "./Course";
import User from "./User";

export default interface Announcement{
    announcementId:number;
    title:string;
    contetn:string;
    author:User;
    time:Date;
    course:Course;
}