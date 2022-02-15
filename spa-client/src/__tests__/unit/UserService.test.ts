/**
 * @jest-environment jsdom
 */

import { userService } from "../../services";
import { course, mockSuccesfulResponse, user1, user2 } from "../Mocks";

test("Should get all users", () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [user1, user2], headers);

  userService.getUsers().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(2);
  });
});

test("Should get user with id 1", () => {
  mockSuccesfulResponse(200, user1);

  userService.getUserById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()).toBe(user1);
  });
});

test("Should get last filenumber", () => {
  mockSuccesfulResponse(200, { nextFileNumber: 1235 });

  userService.getLastFileNumber().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().nextFileNumber).toBe(1235);
  });
});

test("Should get user courses", () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [course], headers);

  userService.getUserCourses(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent()[0]).toBe(course);
  });
});

test("Should create a new user", () => {
  mockSuccesfulResponse(204, []);

  userService
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

test("Should not create a new user due to password not match confirm password", () => {
  userService
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
      expect(response.getError().getCode()).toBe(422);
    });
});

test("Should not send email due to empty title and content", () => {
  userService.sendEmail(1, 1, "", "").then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(422);
  });
});
