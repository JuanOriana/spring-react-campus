/**
 * @jest-environment jsdom
 */
import { fileService } from "../../services";
import {
  answerFile,
  categoryTheory,
  extensionPdf,
  extensionTxt,
  mockSuccesfulResponse,
} from "../Mocks";

test("Should get files", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");

  mockSuccesfulResponse(200, [answerFile], headers);

  return fileService
    .getFiles([1], [2], "answ", "date", "asc", 1, 2)
    .then((resp) => {
      expect(resp.hasFailed()).toBeFalsy();
      expect(resp.getData().getContent()[0]).toBe(answerFile);
    });
});

test("Should get files extensions", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [extensionPdf, extensionTxt], headers);

  return fileService.getExtensions().then((resp) => {
    expect(resp.hasFailed()).toBeFalsy();
    expect(resp.getData().getContent().length).toBe(2);
    expect(resp.getData().getContent()[0]).toBe(extensionPdf);
  });
});

test("Should get files extension by id", async () => {
  mockSuccesfulResponse(200, extensionTxt);

  return fileService.getExtensionbyById(1).then((resp) => {
    expect(resp.hasFailed()).toBeFalsy();
    expect(resp.getData()).toBe(extensionTxt);
  });
});

test("Should get files category by id", async () => {
  mockSuccesfulResponse(200, categoryTheory);

  return fileService.getCategoryById(1).then((resp) => {
    expect(resp.hasFailed()).toBeFalsy();
    expect(resp.getData()).toBe(categoryTheory);
  });
});

test("Should get files categories", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "2");
  mockSuccesfulResponse(200, [categoryTheory], headers);

  return fileService.getCategories().then((resp) => {
    expect(resp.hasFailed()).toBeFalsy();
    expect(resp.getData().getMaxPage()).toBe(2);
    expect(resp.getData().getContent()[0]).toBe(categoryTheory);
  });
});
