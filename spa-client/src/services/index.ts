import { CourseService } from "./CoursesService";
import { AnnouncementsService } from "./AnnouncementsService";
import { LoginService } from "./LoginService";

const courseService = new CourseService();
const announcementsService = new AnnouncementsService();
const loginService = new LoginService();

export { courseService, announcementsService, loginService };
