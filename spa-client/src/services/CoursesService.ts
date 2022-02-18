import {
  APPLICATION_V1_JSON_TYPE,
  paths,
  userRoles,
} from "../common/constants";
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
  SolvedExamModel,
  RoleModel,
} from "../types";
import AnswerModel from "../types/AnswerModel";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { fileUrlMaker } from "../scripts/fileUrlMaker";
import { resultFetch } from "../scripts/resultFetch";
import ExamStatsModel from "../types/ExamStatsModel";
import { parseAnnouncementResponse } from "../scripts/parseAnnouncementResponse";
import { parseAnswersResponse } from "../scripts/parseAnswersResponse";
import { parseExamModelResponse } from "../scripts/parseExamModelResponse";
import { parseFileResponse } from "../scripts/parseFileResponse";

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
    let url = new URL(this.basePath);
    if (year) {
      url.searchParams.append("year", year.toString());
    }

    if (quarter) {
      url.searchParams.append("quarter", quarter.toString());
    }
    return getPagedFetch<CourseModel[]>(url.toString(), page, pageSize);
  }

  public async getCoursesUnpaged(
    batchSize: number
  ): Promise<Result<CourseModel[]>> {
    let currentPage = 1;
    let maxPage = 1;
    const allResults: CourseModel[] = [];
    while (currentPage <= maxPage) {
      const batch = await this.getCourses(currentPage, batchSize);
      if (batch.hasFailed()) return Result.failed(batch.getError());
      allResults.push(...batch.getData().getContent());
      maxPage = batch.getData().getMaxPage();
      currentPage++;
    }
    return Result.ok(allResults);
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

  public async getPrivileged(
    courseId: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(
      this.basePath + "/" + courseId + "/privileged"
    );
  }

  public async enrollUserToCourse(
    courseId: number,
    userId: number,
    roleId: number
  ): Promise<Result<PostResponse>> {
    const userPaths = ["/students", "/teachers", "/helpers"];
    if (roleId < userRoles.STUDENT || roleId > userRoles.HELPER) {
      return Result.failed(new ErrorResponse(409, "Invalid role id"));
    }

    const userInfo = JSON.stringify({ userId: userId });

    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + userPaths[roleId - 1],
      {
        method: "POST",
        headers: { "Content-Type": APPLICATION_V1_JSON_TYPE },
        body: userInfo,
      }
    );
  }

  public async getStudents(
    courseId: number,
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(
      this.basePath + "/" + courseId + "/students",
      page,
      pageSize
    );
  }
  public async getExams(courseId: number): Promise<Result<ExamStatsModel[]>> {
    return resultFetch<ExamStatsModel[]>(
      this.basePath + "/" + courseId + "/exams",
      { method: "GET" }
    );
  }

  public async getSolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<SolvedExamModel[]>>> {
    const resp = await getPagedFetch<SolvedExamModel[]>(
      this.basePath + "/" + courseId + "/exams/solved"
    );

    if (!resp.hasFailed()) {
      resp
        .getData()
        .getContent()
        .forEach((item) => {
          item.answer.deliveredDate = item.answer.deliveredDate
            ? new Date(item.answer.deliveredDate)
            : undefined;
          item.exam.endTime = item.exam.endTime
            ? new Date(item.exam.endTime)
            : undefined;
          item.exam.startTime = item.exam.startTime
            ? new Date(item.exam.startTime)
            : undefined;
        });
    }
    return resp;
  }

  public async getUnsolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    const resp = await getPagedFetch<ExamModel[]>(
      this.basePath + "/" + courseId + "/exams/unsolved"
    );

    return parseExamModelResponse(resp);
  }

  public async getExamsAverage(
    courseId: number
  ): Promise<Result<{ average: number }>> {
    return resultFetch<{ average: number }>(
      this.basePath + "/" + courseId + "/exams/average",
      {
        method: "GET",
      }
    );
  }

  public async getCourseAnswers(
    courseId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    const resp = await getPagedFetch<AnswerModel[]>(
      this.basePath + "/" + courseId + "/exams/answers"
    );

    return parseAnswersResponse(resp);
  }

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
    const resp = await getPagedFetch<AnnouncementModel[]>(
      this.basePath + "/" + courseId + "/announcements"
    );
    return parseAnnouncementResponse(resp);
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
      orderDirection
    );

    const resp = await getPagedFetch<FileModel[]>(
      url.toString(),
      page,
      pageSize
    );
    return parseFileResponse(resp);
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
    startTimes = startTimes.map((num) => parseInt(num.toString()));
    endTimes = endTimes.map((num) => parseInt(num.toString()));

    const newCourse = JSON.stringify({
      subjectId: subjectId,
      quarter: quarter,
      board: board,
      year: year,
      startTimes: startTimes,
      endTimes: endTimes,
    });
    for (let index = 0; index < 6; index++) {
      if (startTimes[index] > endTimes[index])
        return Result.failed(
          new ErrorResponse(409, "EndTime must be greater than startime ")
        );
    }

    return resultFetch<PostResponse>(this.basePath, {
      method: "POST",
      headers: { "Content-Type": APPLICATION_V1_JSON_TYPE },
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
      redirectLink: paths.LOCAL_BASE_URL,
    });

    return resultFetch<PostResponse>(
      this.basePath + "/" + courseId + "/announcements",
      {
        method: "POST",
        headers: { "Content-Type": APPLICATION_V1_JSON_TYPE },
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
      return Result.failed(new ErrorResponse(409, "File field cannot be null"));
    }
    if (startTime >= endTime) {
      return Result.failed(
        new ErrorResponse(
          409,
          "Starttime cannot be greater or equal than endtime"
        )
      );
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
      return Result.failed(new ErrorResponse(409, "Category must not be null"));
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

  public async getRole(courseId: number) {
    return resultFetch<RoleModel>(this.basePath + "/" + courseId + "/role", {
      method: "GET",
    });
  }
}
