import { Result } from "../types/Results";
import Course from "../types/Course";
import { paths } from "../common/constants";
import { ErrorResponse } from "../types/ErrorResponse";
import { checkError } from "../scripts/ErrorChecker";
import { authedFetch } from "../scripts/authedFetch";
import User from "../types/User";
import Exam from "../types/Exam";
import Announcement from "../types/Announcement";

export class CourseService {
  private readonly basePath = paths.BASE_URL + paths.COURSES;

  public async getCourseById(courseId: number): Promise<Result<Course>> {
    try {
      const response = await authedFetch(this.basePath + "/" + courseId, {
        method: "GET",
      });
      const parsedResponse = await checkError<Course>(response);
      return Result.ok(parsedResponse as Course);
    } catch (err: any) {
      return Result.failed(
        new ErrorResponse(parseInt(err.message), err.message)
      );
    }
  }

  public async getCourses(
    page?: number,
    pageSize?: number
  ): Promise<Result<Course[]>> {
    let url = new URL(this.basePath);

    if (typeof page !== "undefined" && typeof pageSize !== "undefined") {
      let params = { page: page.toString(), pageSize: pageSize.toString() };
      url.search = new URLSearchParams(params).toString();
    }

    try {
      const response = await authedFetch(url.toString(), {
        method: "GET",
      });
      const parsedResponse = await checkError<Course[]>(response);
      return Result.ok(parsedResponse as Course[]);
    } catch (err: any) {
      return Result.failed(
        new ErrorResponse(parseInt(err.message), err.message)
      );
    }
  }

  public async getHelpers(courseId: number): Promise<Result<User>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/helpers",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<User>(response);

      return Result.ok(parsedResponse as User);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getTeachers(courseId: number): Promise<Result<User>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/teachers",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<User>(response);

      return Result.ok(parsedResponse as User);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getStudents(courseId: number): Promise<Result<User>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/students",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<User>(response);

      return Result.ok(parsedResponse as User);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getExams(courseId: number): Promise<Result<Exam>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<Exam>(response);

      return Result.ok(parsedResponse as Exam);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getSolvedExams(courseId: number): Promise<Result<Exam>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams/solved",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<Exam>(response);

      return Result.ok(parsedResponse as Exam);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getUnsolvedExams(courseId: number): Promise<Result<Exam>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams/unsolved",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<Exam>(response);

      return Result.ok(parsedResponse as Exam);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  //   public async getCourseAnswers(courseId: number): Promise<Result<Answer>> {
  //     try { // TODO: Esperar respuesta mail para ver que hacer con los links en este caso
  //       const response = await authedFetch(
  //         this.basePath + "/" + courseId + "/exams/answers",
  //         {
  //           method: "GET",
  //         }
  //       );
  //       const parsedResponse = await checkError<Exam>(response);

  //       return Result.ok(parsedResponse as Exam);
  //     } catch (error: any) {
  //       return Result.failed(
  //         new ErrorResponse(parseInt(error.message), error.message)
  //       );
  //     }
  //   }

  //TODO: Ver si este service puede mapear el json sin el type! (cuando podamos correr la api)
  public async getAvailableYears(): Promise<Result<number>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + "available-years",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<number>(response);

      return Result.ok(parsedResponse as number);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getAnnouncemets(
    courseId: number
  ): Promise<Result<Announcement>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/announcements",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<Announcement>(response);

      return Result.ok(parsedResponse as Announcement);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }
}
