import { paths } from "../common/constants";
import { AnnouncementModel, PagedContent, Result } from "../types";
import { getFetch } from "../scripts/getFetch";
import { getPagedFetch } from "../scripts/getPagedFetch";
import { pageUrlMaker } from "../scripts/pageUrlMaker";

export class AnnouncementsService {
  private readonly basePath = paths.BASE_URL + paths.ANNOUNCEMENTS;
  public async getAnnouncements(
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<AnnouncementModel[]>>> {
    let url = pageUrlMaker(this.basePath, page, pageSize);
    return getPagedFetch<AnnouncementModel[]>(url.toString());
  }

  public async getAnnouncementById(
    announcementId: number
  ): Promise<Result<AnnouncementModel>> {
    return getFetch<AnnouncementModel>(this.basePath + "/" + announcementId);
  }
}
