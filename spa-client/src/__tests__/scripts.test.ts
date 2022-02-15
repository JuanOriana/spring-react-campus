/**
 * @jest-environment jsdom
 */
import { checkError } from "../scripts/ErrorChecker";
import { mockSuccesfulResponse, user1 } from "./Mocks";

export {};

test("", () => {});

// TODO: Complete
// test("Should login with cached credentials", () => {
//   const headers = new window.Headers();
//   headers.set("Authorization", "Bearer token");

//   mockSuccesfulResponse(401, user1, headers, [
//     {
//       name: "rememberMe",
//       value: "true",
//     },
//     { name: "basic-token", value: "basic" },
//   ]);

//   fetch("")
//     .then((resp) => {
//       let a = resp.json();
//       console.log(resp);
//       console.log(a);
//     })
//     .catch((err) => {
//       checkError(err);
//       console.log(localStorage.getItem("token"));
//     });
// });
