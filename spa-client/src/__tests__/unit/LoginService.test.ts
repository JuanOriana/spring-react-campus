/**
 * @jest-environment jsdom
 */
import { loginService } from "../../services";
import { mockSuccesfulResponse, user1 } from "../Mocks";

test("Should return a valid token", () => {
  const headers = new window.Headers();
  headers.set("Authorization", "Bearer token");
  mockSuccesfulResponse(200, user1, headers);

  loginService.login("username", "password").then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().token).toBe("token");
  });
});

test("Should not return a valid token", () => {
  mockSuccesfulResponse(401, user1);

  loginService.login("username", "password").then((response) => {
    expect(response.hasFailed()).toBeTruthy();
    expect(response.getError().getCode()).toBe(401);
  });
});
