import {
  AnnouncementModel,
  CourseModel,
  ExamModel,
  UserModel,
  AnswerModel,
  FileModel,
  SolvedExamModel,
  RoleModel,
  SubjectModel,
} from "../../types";
import { LocalStorageMock } from "./LocalStorageMock";

export const subject: SubjectModel = { subjectId: 1, code: "A", name: "PAW" };

export const course: CourseModel = {
  courseId: 1,
  year: 2022,
  quarter: 1,
  board: "BT",
  subject: subject,
  courseUrl: "/courses",
};

export const user1: UserModel = {
  userId: 1,
  name: "Santiago",
  surname: "Garcia",
  username: "sangarcai",
  email: "sang@gmail.com",
  fileNumber: 123,
  admin: false,
};
export const user2: UserModel = {
  userId: 2,
  name: "Marcelo",
  surname: "Garcia",
  username: "marcelogarcia",
  email: "marcelog@gmail.com",
  fileNumber: 1234,
  admin: false,
};

export const announcement: AnnouncementModel = {
  announcementId: 1,
  title: "Prueba",
  content: "Id 1",
  author: user1,
  date: new Date(),
  course: course,
};

export const exam: ExamModel = {
  examId: 1,
  course: course,
  startTime: new Date("2022-02-15T14:47:27.306Z"),
  endTime: new Date(),
  title: "Titulo",
  description: "Descripcion",
  average: 0,
  url: "url",
};
export const examUnfefEndtime: ExamModel = {
  examId: 1,
  course: course,
  startTime: new Date("2022-02-15T14:47:27.306Z"),
  endTime: undefined,
  title: "Titulo",
  description: "Descripcion",
  average: 0,
  url: "url",
};

export const answerFile: FileModel = {
  fileId: 1,
  extension: {
    fileExtensionId: 1,
    fileExtensionName: "txt",
  },
  course: course,
  downloads: 1,
};

export const answer: AnswerModel = {
  answerId: 1,
  examUrl: "url",
  answerUrl: "url",
  student: user1,
  teacher: user2,
  answerFile: answerFile,
  score: 10,
  corrections: "Excelente",
};

export const examSolved: SolvedExamModel = {
  exam: exam,
  answer: answer,
};

export const studentRole: RoleModel = {
  roleId: 1,
  roleName: "Estudiante",
};

export function mockSuccesfulResponse(
  code: number,
  returnBody: any,
  headers?: Headers,
  cookies?: { name: string; value: string }[]
) {
  global.localStorage = new LocalStorageMock();
  if (typeof cookies !== "undefined") {
    cookies.forEach((cookie) => {
      global.localStorage.setItem(cookie.name, cookie.value);
    });
  }
  return (global.fetch = jest.fn().mockImplementationOnce(() => {
    return new Promise((resolve, reject) => {
      resolve({
        ok: true,
        status: code,
        headers: headers,
        json: () => {
          return returnBody;
        },
      });
    });
  }));
}
