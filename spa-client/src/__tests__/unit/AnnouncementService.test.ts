/**
 * @jest-environment jsdom
 */
import { announcementsService } from "../../services";
import { announcement, mockSuccesfulResponse } from "../Mocks";

test("Should get an announcement", async () => {
  mockSuccesfulResponse(200, announcement);
  return announcementsService.getAnnouncementById(1).then((ans) => {
    expect(ans.hasFailed()).toBeFalsy();
    expect(ans.getData().announcementId).toBe(1);
  });
});

test("Should get all announcements", async () => {
  const headers = new window.Headers();
  headers.set("x-total-pages", "1");
  mockSuccesfulResponse(200, [announcement], headers);
  return announcementsService.getAnnouncements().then((ans) => {
    expect(ans.hasFailed()).toBeFalsy();
    expect(ans.getData().getContent()[0]).toBe(announcement);
  });
});
