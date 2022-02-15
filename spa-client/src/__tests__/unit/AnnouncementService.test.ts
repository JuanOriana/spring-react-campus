import { announcementsService } from "../../services";
import { announcement, mockSuccesfulResponse } from "../Mocks";



test("Should get an announcement", () => {
  mockSuccesfulResponse(200,announcement);
  announcementsService.getAnnouncementById(1).then(ans=>
    {
     expect(ans.hasFailed()).toBeFalsy();
     expect(ans.getData().announcementId).toBe(1); 
    }
  );
});




