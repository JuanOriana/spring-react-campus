/**
 * @jest-environment jsdom
 */

import { courseService } from "../../services";
import { HeadersMock } from "./HeadersMock";
import {
  announcement,
  answer,
  course,
  examSolved,
  mockSuccesfulResponse,
  studentRole,
  user1,
  user2,
} from "./Mocks";

test("Should get a course with id=1", () => {
  mockSuccesfulResponse(200, course);
  courseService.getCourseById(1).then((courseResponse) => {
    expect(courseResponse.hasFailed()).toBeFalsy();
    expect(courseResponse.getData()).toStrictEqual(course);
  });
});

test("Should get all courses", () => {
  const head = new HeadersMock();
  head.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [course], head);
  courseService.getCourses().then((courseResponse) => {
    expect(courseResponse.hasFailed()).toBeFalsy();
    const page = courseResponse.getData().getContent();
    expect(page).toStrictEqual([course]);
  });
});

test("Should get all helpers from course", () => {
  const head = new HeadersMock();
  head.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1], head);
  courseService.getHelpers(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    const page = response.getData().getContent();
    expect(page).toStrictEqual([user1]);
  });
});

test("Should enroll user to course", () => {
  mockSuccesfulResponse(204, {});
  courseService.enrollUserToCourse(1, 1, 1).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204); // In a succesful POST a 204 is expected.
  });
});

test("Should not enroll  user to course due to invalid roleid", () => {
  mockSuccesfulResponse(204, {});
  courseService.enrollUserToCourse(1, 1, 0).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(422); // In a succesful POST a 204 is expected.
  });
});

test("Should retrieve all students from a course", () => {
  const headers = new HeadersMock();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1, user2], headers);
  courseService.getStudents(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    const page = response.getData().getContent();
    expect(page[0]).toBe(user1);
  });
});

test("Should get solved exams", () => {
  const headers = new HeadersMock();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [examSolved], headers);

  courseService.getSolvedExams(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(examSolved);
  });
});

test("Should not get solved exams due to be empty", () => {
  mockSuccesfulResponse(204, {});

  courseService.getSolvedExams(1).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204);
  });
});

test("Should get exams average", () => {
  mockSuccesfulResponse(200, { average: 1 });

  courseService.getExamsAverage(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().average).toBe(1);
  });
});

test("Should get answers from course", () => {
  const headers = new HeadersMock();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [answer], headers);

  courseService.getCourseAnswers(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(answer);
  });
});

test("Should get available years", () => {
  mockSuccesfulResponse(200, { years: [2021, 2022] });

  courseService.getAvailableYears().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().years.length).toBe(2);
  });
});

test("Should get announcement from course", () => {
  const headers = new HeadersMock();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [announcement], headers);

  courseService.getAnnouncements(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(announcement);
  });
});

test("Should create new course", () => {
  mockSuccesfulResponse(204, {});

  courseService.newCourse(1, 2, "ADA", 2022, [1], [2]).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204);
  });
});

test("Should not create new course due to bad startime", () => {
  mockSuccesfulResponse(204, {});

  courseService.newCourse(1, 2, "ADA", 2022, [3], [2]).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(422);
  });
});

test("Should create an announcement", () => {
  mockSuccesfulResponse(204, {});
  courseService
    .newAnnouncement(course.courseId, announcement.title, announcement.content)
    .then((ans) => {
      expect(ans.hasFailed()).toBeTruthy();
      expect(ans.getError().getCode()).toBe(204);
    });
});

test("Should create a new exam", () => {
  mockSuccesfulResponse(204, {});

  courseService
    .newExam(
      1,
      "title",
      "content",
      new window.File([], ""),
      new Date("2011-04-11T10:20:30"),
      new Date("2022-04-11T10:20:30")
    )
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(204);
    });
});

test("Should not create a new exam due to null file", () => {
  mockSuccesfulResponse(204, {});

  courseService
    .newExam(
      1,
      "title",
      "content",
      null,
      new Date("2011-04-11T10:20:30"),
      new Date("2022-04-11T10:20:30")
    )
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(422);
    });
});

test("Should not create a new exam due to bad files", () => {
  mockSuccesfulResponse(204, {});

  courseService
    .newExam(
      1,
      "title",
      "content",
      new window.File([], ""),
      new Date("2022-04-11T10:20:30"),
      new Date("2021-04-11T10:20:30")
    )
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(422);
    });
});

test("Should create a new file", () => {
  mockSuccesfulResponse(204, {});

  courseService.newFile(1, new window.File([], ""), 2).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204);
  });
});

test("Should get roles", () => {
  mockSuccesfulResponse(200, studentRole);

  courseService.getRole(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()).toBe(studentRole);
  });
});

// test("Should create a new exam",()=>{
//     mockSuccesfulResponse(204,{});

//      courseService.newExam(1,"title","content",null, new Date("2022-02-15T15:58:41.837Z"),new Date())
//      .then(response=>{
//          expect(response.hasFailed()).toBeTruthy();
//          expect(response.getError().getCode()).toBe(204);
//      })
// });
