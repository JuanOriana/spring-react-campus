import { AnnouncementModel, PagedContent, Result } from "../types";

export function parseAnnouncementResponse(
  announcements: Result<PagedContent<AnnouncementModel[]>>
): Result<PagedContent<AnnouncementModel[]>> {
  if (!announcements.hasFailed()) {
    announcements
      .getData()
      .getContent()
      .forEach(
        (announcement) => (announcement.date = new Date(announcement.date))
      );
  }

  return announcements;
}
