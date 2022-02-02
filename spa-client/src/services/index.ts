import { CourseService } from "./CoursesService";
import { AnnouncementsService } from "./AnnouncementsService";
import { LoginService } from "./LoginService";
import { UserService } from "./UserService";

const courseService = new CourseService();
const announcementsService = new AnnouncementsService();
const loginService = new LoginService();
const userService = new UserService();

export { courseService, announcementsService, loginService, userService };
