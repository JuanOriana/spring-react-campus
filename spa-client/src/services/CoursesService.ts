import { Result } from "../../types/Results";
import Course from "../../types/Course"
import { paths } from "../common/constants";

export class CourseService{


    public async getCourseById(courseId:number):Promise<Result<Course>>
    {
    
        const response = await fetch(paths.BASE_URL+paths.COURSES+"/"+courseId,
        {
            method: 'GET'
        });
    
        return response.json();
    }



}