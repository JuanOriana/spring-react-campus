import { paths } from "../common/constants";
import { AnnouncementModel, Result, ErrorResponse } from "../types";
import { authedFetch } from "../scripts/authedFetch";
import { checkError } from "../scripts/ErrorChecker";
import { getFetch } from "../scripts/getFetch";
export class AnnouncementsService {
  private readonly basePath = paths.BASE_URL + paths.ANNOUNCEMENTS;
  public async getAnnouncements(
    page?: number,
    pageSize?: number
  ): Promise<Result<AnnouncementModel[]>> {
    let url = new URL(this.basePath);
    let params = new URLSearchParams();
    if (typeof page !== "undefined") {
      url = new URL(paths.BASE_URL + paths.ANNOUNCEMENTS_QUERY_PARAMS);
      params.append("page", page.toString());
    }

    if (typeof pageSize !== "undefined") {
      url = new URL(paths.BASE_URL + paths.ANNOUNCEMENTS_QUERY_PARAMS);
      params.append("pageSize", pageSize.toString());
      url.search = params.toString();
    }
    return getFetch<AnnouncementModel[]>(url.toString());
  }

  public async getAnnouncementById(
    announcementId: number
  ): Promise<Result<AnnouncementModel>> {
    return getFetch<AnnouncementModel>(this.basePath + announcementId);
  }
}
