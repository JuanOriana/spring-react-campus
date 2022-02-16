/**
 * @jest-environment jsdom
 */
import { subjectsService } from "../../services";
import { mockSuccesfulResponse, subject } from "../Mocks";

test("Should get subject with id 1", async () => {
  mockSuccesfulResponse(200, subject);

  return subjectsService.getSubjectById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().subjectId).toBe(subject.subjectId);
  });
});

test("Should get all subjects", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [subject], headers);
  return subjectsService.getSubjects().then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent()[0]).toBe(subject);
  });
});

test("Should get all subjects unpaged", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [subject], headers);
  return subjectsService.getSubjectsUnpaged(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData()[0]).toBe(subject);
  });
});
