/**
 * @jest-environment jsdom
 */
import { examsService } from "../../services";
import { exam, examUnfefEndtime, mockSuccesfulResponse } from "../Mocks";

test("Should get exam", () => {
  mockSuccesfulResponse(200, exam);

  examsService.getExamById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().examId).toBe(exam.examId);
  });
});

test("Should get exam with undefined endtime", () => {
  mockSuccesfulResponse(200, examUnfefEndtime);

  examsService.getExamById(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().examId).toBe(exam.examId);
    expect(typeof response.getData().endTime).toBe("undefined");
  });
});

test("Should get all answers from exam with id 1 ", () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [exam, examUnfefEndtime], headers);

  examsService.getExamAnswers(1).then((response) => {
    expect(response.hasFailed()).toBeFalsy();
    expect(response.getData().getContent().length).toBe(2);
  });
});
