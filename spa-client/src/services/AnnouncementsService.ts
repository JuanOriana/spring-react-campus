import { paths } from "../common/constants";
import { AnnouncementModel, PagedContent, Result } from "../types";
import { getFetch } from "../scripts/getFetch";
import { getPagedFetch } from "../scripts/getPagedFetch";

export class AnnouncementsService {
  private readonly basePath = paths.BASE_URL + paths.ANNOUNCEMENTS;
  public async getAnnouncements(
    page?: number,
    pageSize?: number
  ): Promise<Result<PagedContent<AnnouncementModel[]>>> {
    let url = new URL(this.basePath);
    let params = new URLSearchParams();
    if (page) {
      params.append("page", page.toString());
    }
    if (pageSize) {
      params.append("pageSize", pageSize.toString());
    }
    url.search = params.toString();
    return getPagedFetch<AnnouncementModel[]>(url.toString());
  }

  public async getAnnouncementById(
    announcementId: number
  ): Promise<Result<AnnouncementModel>> {
    return getFetch<AnnouncementModel>(this.basePath + "/" + announcementId);
  }
}
