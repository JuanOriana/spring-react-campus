import { CourseService } from "./CoursesService";
import {AnnouncementsService} from "./AnnouncementsService"

const courseService = new CourseService();
const announcementsService = new AnnouncementsService();

export{
    courseService,
    announcementsService
}