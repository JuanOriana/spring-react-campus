/**
 * @jest-environment jsdom
 */

import { courseService } from "../../services";
import {
  announcement,
  answer,
  course,
  examSolved,
  mockSuccesfulResponse,
  studentRole,
  user1,
  user2,
} from "../Mocks";

test("Should get a course with id=1", async () => {
  mockSuccesfulResponse(200, course);
  return courseService.getCourseById(1).then((courseResponse) => {
    expect(courseResponse.hasFailed()).toBeFalsy();
    expect(courseResponse.getData()).toStrictEqual(course);
  });
});

test("Should get all courses paged", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [course], headers);
  return courseService.getCoursesUnpaged(1).then((courseResponse) => {
    expect(courseResponse.hasFailed()).toBeFalsy();
    expect(courseResponse.getData()[0]).toStrictEqual(course);
  });
});

test("Should get all helpers from course", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1], headers);
  return courseService.getHelpers(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    const page = response.getData().getContent();
    expect(page).toStrictEqual([user1]);
  });
});

test("Should enroll user to course", async () => {
  mockSuccesfulResponse(204, {});
  return courseService.enrollUserToCourse(1, 1, 1).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204); // In a succesful POST a 204 is expected.
  });
});

test("Should not enroll  user to course due to invalid roleid", async () => {
  mockSuccesfulResponse(204, {});
  return courseService.enrollUserToCourse(1, 1, 0).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(409); // In a succesful POST a 204 is expected.
  });
});

test("Should retrieve all students from a course", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1, user2], headers);
  return courseService.getStudents(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    const page = response.getData().getContent();
    expect(page[0]).toBe(user1);
  });
});

test("Should get solved exams", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [examSolved], headers);

  return courseService.getSolvedExams(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(examSolved);
  });
});

test("Should not get solved exams due to be empty", async () => {
  mockSuccesfulResponse(204, {});

  return courseService.getSolvedExams(1).then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204);
  });
});

test("Should get exams average", async () => {
  mockSuccesfulResponse(200, { average: 1 });

  return courseService.getExamsAverage(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().average).toBe(1);
  });
});

test("Should get answers from course", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [answer], headers);

  return courseService.getCourseAnswers(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(answer);
  });
});

test("Should get available years", async () => {
  mockSuccesfulResponse(200, { years: [2021, 2022] });

  return courseService.getAvailableYears().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().years.length).toBe(2);
  });
});

test("Should get announcement from course", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [announcement], headers);

  return courseService.getAnnouncements(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(1);
    expect(response.getData().getContent()[0]).toBe(announcement);
  });
});

test("Should create new course", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
    .newCourse(1, 2, "ADA", 2022, [1], [2])
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(204);
    });
});

test("Should not create new course due to bad startime", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
    .newCourse(1, 2, "ADA", 2022, [3], [2])
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(409);
    });
});

test("Should create an announcement", async () => {
  mockSuccesfulResponse(204, {});
  return courseService
    .newAnnouncement(course.courseId, announcement.title, announcement.content)
    .then((ans) => {
      expect(ans.hasFailed()).toBeTruthy();
      expect(ans.getError().getCode()).toBe(204);
    });
});

test("Should create a new exam", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
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

test("Should not create a new exam due to null file", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
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
      expect(response.getError().getCode()).toBe(409);
    });
});

test("Should not create a new exam due to bad files", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
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
      expect(response.getError().getCode()).toBe(409);
    });
});

test("Should create a new file", async () => {
  mockSuccesfulResponse(204, {});

  return courseService
    .newFile(1, new window.File([], ""), 2)
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(204);
    });
});

test("Should get roles", async () => {
  mockSuccesfulResponse(200, studentRole);

  return courseService.getRole(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()).toBe(studentRole);
  });
});
