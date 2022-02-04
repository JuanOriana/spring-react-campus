import { paths } from "../common/constants";
import {
  Result,
  CourseModel,
  AnnouncementModel,
  UserModel,
  ExamModel,
  PagedContent,
} from "../types";
import AnswerModel from "../types/AnswerModel";
import { getFetch } from "../scripts/getFetch";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";
import { authedFetch } from "../scripts/authedFetch";
import { postFetch } from "../scripts/postFetch";
import { string } from "prop-types";

export class CourseService {
  private readonly basePath = paths.BASE_URL + paths.COURSES;

  public async getCourseById(courseId: number): Promise<Result<CourseModel>> {
    return getFetch<CourseModel>(this.basePath + courseId);
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
    return getPagedFetch<UserModel[]>(this.basePath + courseId + "/helpers");
  }

  public async getTeachers(
    courseId: number
  ): Promise<Result<PagedContent<UserModel[]>>> {
    return getPagedFetch<UserModel[]>(this.basePath + courseId + "/teachers");
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
    return getPagedFetch<ExamModel[]>(this.basePath + courseId + "/exams");
  }

  public async getSolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    return getPagedFetch<ExamModel[]>(
      this.basePath + courseId + "/exams/solved"
    );
  }

  public async getUnsolvedExams(
    courseId: number
  ): Promise<Result<PagedContent<ExamModel[]>>> {
    return getPagedFetch<ExamModel[]>(
      this.basePath + courseId + "/exams/unsolved"
    );
  }

  public async getCourseAnswers(
    courseId: number
  ): Promise<Result<PagedContent<AnswerModel[]>>> {
    // TODO: Esperar respuesta mail para ver que hacer con los links en este caso
    return getPagedFetch<AnswerModel[]>(
      this.basePath + courseId + "/exams/answers"
    );
  }

  //TODO: Ver si este service puede mapear el json sin el type! (cuando podamos correr la api)
  public async getAvailableYears(): Promise<Result<number>> {
    return getFetch<number>(this.basePath + "available-years");
  }

  public async getAnnouncements(
    courseId: number
  ): Promise<Result<PagedContent<AnnouncementModel[]>>> {
    return getPagedFetch<AnnouncementModel[]>(
      this.basePath + courseId + "/announcements"
    );
  }

  public async newCourse(
    subjectId: number,
    quarter: number,
    board: string,
    year: number,
    startTimes: number[],
    endTimes: number[]
  ) {
    const newCourse = JSON.stringify({
      subjectId: subjectId,
      quarter: quarter,
      board: board,
      year: year,
      startTimes: startTimes,
      endTimes: endTimes,
    });

    return postFetch(
      this.basePath,
      "application/vnd.campus.api.v1+json",
      newCourse
    );
  }

  public async newAnnouncement(
    courseId: number,
    title: string,
    content: string
  ) {
    const newAnnouncement = JSON.stringify({
      title: title,
      content: content,
    });

    return postFetch(
      this.basePath + "/" + courseId + "/announcements",
      "application/vnd.campus.api.v1+json",
      newAnnouncement
    );
  }

  public async newExam(
    courseId: number,
    title: string,
    content: string,
    file: File,
    startTime: string,
    endTime: string
  ) {
    const newExam = JSON.stringify({
      title: string,
      content: string,
      file: file, // TODO ver si esto es correcto
      startTime: startTime,
      endTime: endTime,
    });
    return postFetch(
      this.basePath + "/" + courseId + "/exams",
      "application/vnd.campus.api.v1+json",
      newExam
    );
  }

  public async newAnswer(courseId: number, answerFile: File) {
    const newAnswer = JSON.stringify({
      exam: answerFile,
    });

    return postFetch(
      this.basePath + "/" + courseId + "/exams",
      "application/vnd.campus.api.v1+json",
      newAnswer
    );
  }

  public async deleteFile(courseId: number, fileId: number) {
    return authedFetch(this.basePath + "/" + courseId + "/files/" + fileId, {
      method: "DELETE",
    });
  }
}
