import { paths } from "../common/constants";
import {
  Result,
  CourseModel,
  AnnouncementModel,
  UserModel,
  ExamModel,
  PagedContent,
  FileModel,
  PostResponse,
  ErrorResponse,
} from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";
import { fileUrlMaker } from "../scripts/fileUrlMaker";
import { resultFetch } from "../scripts/resultFetch";
import ExamStatsModel from "../types/ExamStatsModel";

export class CourseService {
  private readonly basePath = paths.BASE_URL + paths.COURSES;

  public async getCourseById(courseId: number): Promise<Result<CourseModel>> {
    return resultFetch<CourseModel>(this.basePath + "/" + courseId, {
      method: "GET",
    });
  }

  public async getCourses(
    page?: number,
    pageSize?: number,
    year?: number,
    quarter?: number
  ): Promise<Result<PagedContent<CourseModel[]>>> {
    let url = pageUrlMaker(this.basePath, page, pageSize);
    if (typeof year !== "undefined") {
      url.searchParams.append("year", year.toString());
    }

    if (typeof quarter !== "undefined") {
      url.searchParams.append("quarter", quarter.toString());
    }
    return getPagedFetch<CourseModel[]>(url.toString());
  }

  public async getHelpers(
    courseId: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(
      this.basePath + "/" + courseId + "/helpers"
    );
  }

  public async getTeachers(
    courseId: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(
      this.basePath + "/" + courseId + "/teachers"
    );
  }

  public async getStudents(
    courseId: number,
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    let url = pageUrlMaker(
      this.basePath + "/" + courseId + "/students",
      page,
      pageSize
    );
    return getPagedFetch<UserModel[]>(url.toString());
  }
  public async getExams(courseId: number): Promise<Result<ExamStatsModel[]>> {
    return resultFetch<ExamStatsModel[]>(
      this.basePath + "/" + courseId + "/exams",
      { method: "GET" }
    );
  }

  public async getSolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    return getPagedFetch<ExamModel[]>(
      this.basePath + "/" + courseId + "/exams/solved"
    );
  }

  public async getUnsolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    return getPagedFetch<ExamModel[]>(
      this.basePath + "/" + courseId + "/exams/unsolved"
    );
  }

  public async getExamsAverage(courseId: number) {
    return resultFetch<number>(
      this.basePath + "/" + courseId + "/exams/average",
      {
        method: "GET",
      }
    );
  }

  public async getCourseAnswers(
    courseId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    return getPagedFetch<AnswerModel[]>(
      this.basePath + "/" + courseId + "/exams/answers"
    );
  }

  //TODO: Ver si este service puede mapear el json sin el type! (cuando podamos correr la api)
  public async getAvailableYears(): Promise<Result<{ years: number[] }>> {
    return resultFetch<{ years: number[] }>(
      this.basePath + "/available-years",
      {
        method: "GET",
      }
    );
  }

  public async getAnnouncements(
    courseId: number
  ): Promise<Result<PagedContent<AnnouncementModel[]>>> {
    return getPagedFetch<AnnouncementModel[]>(
      this.basePath + "/" + courseId + "/announcements"
    );
  }

  public async getFiles(
    courseId: number,
    categoryType?: number[],
    extensionType?: number[],
    query?: string,
    orderProperty?: string,
    orderDirection?: string,
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<FileModel[]>>> {
    let url = fileUrlMaker(
      this.basePath + "/" + courseId + "/files",
      categoryType,
      extensionType,
      query,
      orderProperty,
      orderDirection,
      page,
      pageSize
    );

    return getPagedFetch<FileModel[]>(url.toString());
  }

  public async getTimes(
    courseId: number
  ): Promise<Result<{ startTime: string; endTime: string }[]>> {
    return resultFetch<{ startTime: string; endTime: string }[]>(
      this.basePath + "/" + courseId + "/timetable",
      { method: "GET" }
    );
  }
  public async newCourse(
    subjectId: number,
    quarter: number,
    board: string,
    year: number,
    startTimes: number[],
    endTimes: number[]
  ): Promise<Result<PostResponse>> {
    const newCourse = JSON.stringify({
      subjectId: subjectId,
      quarter: quarter,
      board: board,
      year: year,
      startTimes: startTimes,
      endTimes: endTimes,
    });

    return resultFetch<PostResponse>(this.basePath, {
      method: "POST",
      headers: { "Content-Type": "application/vnd.campus.api.v1+json" },
      body: newCourse,
    });
  }

  public async newAnnouncement(
    courseId: number,
    title: string,
    content: string
  ): Promise<Result<PostResponse>> {
    const newAnnouncement = JSON.stringify({
      title: title,
      content: content,
    });

    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + "/announcements",
      {
        method: "POST",
        headers: { "Content-Type": "application/vnd.campus.api.v1+json" },
        body: newAnnouncement,
      }
    );
  }

  public async newExam(
    courseId: number,
    title: string,
    content: string,
    file: File | null,
    startTime: Date,
    endTime: Date
  ): Promise<Result<PostResponse>> {
    if (file === null) {
      return Result.failed(new ErrorResponse(422, "File field cannot be null"));
    }
    if (startTime >= endTime) {
      return Result.failed(
        new ErrorResponse(
          422,
          "Starttime cannot be greater or equal than endtime"
        )
      ); // TODO ver si este status code corresponde
    }

    const newExam = new FormData();
    newExam.append("file", file, file.name);

    const metadata = JSON.stringify({
      title: title,
      content: content,
      startTime: startTime.toString(),
      endTime: endTime.toString(),
    });
    newExam.append("metadata", metadata);

    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + "/exams",
      {
        method: "POST",
        headers: {},
        body: newExam,
      }
    );
  }

  public async newFile(
    courseId: number,
    file: File,
    categoryId: number
  ): Promise<Result<PostResponse>> {
    const formData = new FormData();

    if (categoryId === null) {
      return Result.failed(new ErrorResponse(422, "Category must not be null"));
    }
    formData.append("file", file, file.name);
    formData.append("category", categoryId.toString());

    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + "/files",
      {
        method: "POST",
        headers: {},
        body: formData,
      }
    );
  }
}
