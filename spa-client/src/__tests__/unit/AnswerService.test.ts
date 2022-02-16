import { answersService } from "../../services";
import { answer, mockSuccesfulResponse } from "../Mocks";

test("Should get answer by id", async () => {
  mockSuccesfulResponse(200, answer);

  return answersService.getAnswerById(1).then((resp) => {
    expect(resp.hasFailed()).toBeFalsy();
    expect(resp.getData()).toBe(answer);
  });
});

test("Should correct answer", async () => {
  mockSuccesfulResponse(204, {});

  return answersService.correctAnswer(1, "Excelente", 10).then((resp) => {
    expect(resp.hasFailed()).toBeTruthy();
    expect(resp.getError().getCode()).toBe(204);
  });
});
