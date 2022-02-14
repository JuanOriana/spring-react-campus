import { announcementsService } from "../../services";

const announcement = {
  announcementId: 1,
  title: "Prueba",
  content: "Id 1",
  author: {
    userId: 1,
    name: "Santiago",
    surname: "Garcia",
    username: "sangarcai",
    email: "sang@gmail.com",
    fileNumber: 123,
    admin: false,
  },
  date: new Date(),
  course: {
    courseId: 1,
    year: 2022,
    quarter: 1,
    board: "BT",
    subject: { subjectId: 1, code: "A", name: "PAW" },
    courseUrl: "/courses",
  },
};

const mockSuccesfulResponse = (status = 200, returnBody: object) => {
  global.fetch = jest.fn().mockImplementationOnce(() => {
    return new Promise((resolve, reject) => {
      resolve({
        ok: true,
        status,
        json: () => {
          return announcement;
        },
      });
    });
  });
};

test("Should get an announcement", () => {
  console.log(announcementsService.getAnnouncementById(1));
});
