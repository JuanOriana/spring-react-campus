/**
 * @jest-environment jsdom
 */
import { subjectsService } from "../../services";
import { mockSuccesfulResponse, subject } from "../Mocks";

test("Should get subject with id 1", () => {
  mockSuccesfulResponse(200, subject);

  subjectsService.getSubjectById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().subjectId).toBe(subject.subjectId);
  });
});

test("Should get all subjects", () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, subject, headers);
  subjectsService.getSubjectById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().subjectId).toBe(subject.subjectId);
  });
});
