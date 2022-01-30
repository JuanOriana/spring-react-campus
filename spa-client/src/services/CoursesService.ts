import { paths } from "../common/constants";
import { checkError } from "../scripts/ErrorChecker";
import { authedFetch } from "../scripts/authedFetch";
import {
  Result,
  CourseModel,
  ErrorResponse,
  AnnouncementModel,
  UserModel,
  ExamModel,
} from "../types";

export class CourseService {
  private readonly basePath = paths.BASE_URL + paths.COURSES;

  public async getCourseById(courseId: number): Promise<Result<CourseModel>> {
    try {
      const response = await authedFetch(this.basePath + "/" + courseId, {
        method: "GET",
      });
      const parsedResponse = await checkError<CourseModel>(response);
      return Result.ok(parsedResponse as CourseModel);
    } catch (err: any) {
      return Result.failed(
        new ErrorResponse(parseInt(err.message), err.message)
      );
    }
  }

  public async getCourses(
    page?: number,
    pageSize?: number
  ): Promise<Result<CourseModel[]>> {
    let url = new URL(this.basePath);

    if (typeof page !== "undefined" && typeof pageSize !== "undefined") {
      let params = { page: page.toString(), pageSize: pageSize.toString() };
      url.search = new URLSearchParams(params).toString();
    }

    try {
      const response = await authedFetch(url.toString(), {
        method: "GET",
      });
      const parsedResponse = await checkError<CourseModel[]>(response);
      return Result.ok(parsedResponse as CourseModel[]);
    } catch (err: any) {
      return Result.failed(
        new ErrorResponse(parseInt(err.message), err.message)
      );
    }
  }

  public async getHelpers(courseId: number): Promise<Result<UserModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/helpers",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<UserModel>(response);

      return Result.ok(parsedResponse as UserModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getTeachers(courseId: number): Promise<Result<UserModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/teachers",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<UserModel>(response);

      return Result.ok(parsedResponse as UserModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getStudents(courseId: number): Promise<Result<UserModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/students",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<UserModel>(response);

      return Result.ok(parsedResponse as UserModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getExams(courseId: number): Promise<Result<ExamModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<ExamModel>(response);

      return Result.ok(parsedResponse as ExamModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getSolvedExams(courseId: number): Promise<Result<ExamModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams/solved",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<ExamModel>(response);

      return Result.ok(parsedResponse as ExamModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }

  public async getUnsolvedExams(courseId: number): Promise<Result<ExamModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/exams/unsolved",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<ExamModel>(response);

      return Result.ok(parsedResponse as ExamModel);
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
  ): Promise<Result<AnnouncementModel>> {
    try {
      const response = await authedFetch(
        this.basePath + "/" + courseId + "/announcements",
        {
          method: "GET",
        }
      );
      const parsedResponse = await checkError<AnnouncementModel>(response);

      return Result.ok(parsedResponse as AnnouncementModel);
    } catch (error: any) {
      return Result.failed(
        new ErrorResponse(parseInt(error.message), error.message)
      );
    }
  }
}
