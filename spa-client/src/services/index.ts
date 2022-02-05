import { CourseService } from "./CoursesService";
import { AnnouncementsService } from "./AnnouncementsService";
import { LoginService } from "./LoginService";
import { UserService } from "./UserService";
import { FileService } from "./FileServices";

const courseService = new CourseService();
const announcementsService = new AnnouncementsService();
const loginService = new LoginService();
const userService = new UserService();
const fileService = new FileService();

export {
  courseService,
  announcementsService,
  loginService,
  userService,
  fileService,
};
