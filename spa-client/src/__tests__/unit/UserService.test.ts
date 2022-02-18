/**
 * @jest-environment jsdom
 */

import { userService } from "../../services";
import {
  course,
  mockSuccesfulResponse,
  studentRole,
  user1,
  user2,
} from "../Mocks";

test("Should get all users", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1, user2], headers);

  return userService.getUsers(1, 10, 20).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(2);
  });
});

test("Should get user with id 1", async () => {
  mockSuccesfulResponse(200, user1);

  return userService.getUserById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()).toBe(user1);
  });
});

test("Should get last filenumber", async () => {
  mockSuccesfulResponse(200, { nextFileNumber: 1235 });

  return userService.getLastFileNumber().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().nextFileNumber).toBe(1235);
  });
});

test("Should get user courses", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [course], headers);

  return userService.getUserCourses(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent()[0]).toBe(course);
  });
});

test("Should create a new user", async () => {
  mockSuccesfulResponse(204, []);

  return userService
    .newUser(
      123,
      "name",
      "surname",
      "username",
      "email",
      "password",
      "password"
    )
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(204);
    });
});

test("Should not create a new user due to password not match confirm password", async () => {
  return userService
    .newUser(
      123,
      "name",
      "surname",
      "username",
      "email",
      "password",
      "confirmepassword"
    )
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(409);
    });
});

test("Should not send email due to empty title and content", async () => {
  return userService.sendEmail(1, 1, "", "").then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(409);
  });
});

test("Should  send mail", async () => {
  mockSuccesfulResponse(204, {});

  return userService.sendEmail(1, 1, "Title", "Content").then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(204);
  });
});

test("Should  update file", async () => {
  mockSuccesfulResponse(204, {});

  return userService
    .updateUserProfileImage(1, new window.File([], "file"))
    .then((response) => {
      expect(response.hasFailed()).toBeTruthy();
      expect(response.getError().getCode()).toBe(204);
    });
});

test("Should get roles", async () => {
  mockSuccesfulResponse(200, [studentRole]);

  return userService.getRoles().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()[0]).toBe(studentRole);
  });
});

test("Should get user un paged", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1, user2], headers);

  return userService.getUsersUnpaged(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()[0]).toBe(user1);
  });
});
