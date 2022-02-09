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

export class CourseService {
  private readonly basePath = paths.BASE_URL + paths.COURSES;

  public async getCourseById(courseId: number): Promise<Result<CourseModel>> {
    return resultFetch<CourseModel>(this.basePath + "/" + courseId, {
      method: "GET",
    });
  }

  public async getCourses(
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<CourseModel[]>>> {
    let url = pageUrlMaker(this.basePath, page, pageSize);
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
  public async getExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    return getPagedFetch<ExamModel[]>(
      this.basePath + "/" + courseId + "/exams"
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

  public async getCourseAnswers(
    courseId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    return getPagedFetch<AnswerModel[]>(
      this.basePath + "/" + courseId + "/exams/answers"
    );
  }

  //TODO: Ver si este service puede mapear el json sin el type! (cuando podamos correr la api)
  public async getAvailableYears(): Promise<Result<number[]>> {
    return resultFetch<number[]>(this.basePath + "/available-years", {
      method: "GET",
    });
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
    file: File,
    startTime: string,
    endTime: string
  ): Promise<Result<PostResponse>> {
    const newExam = JSON.stringify({
      title: title,
      content: content,
      file: file, // TODO ver si esto es correcto
      startTime: startTime,
      endTime: endTime,
    });
    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + "/exams",
      {
        method: "POST",
        headers: { "Content-Type": "application/vnd.campus.api.v1+json" },
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
