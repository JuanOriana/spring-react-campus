package ar.edu.itba.paw.webapp.common.mappers;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.dto.announcement.AnnouncementDto;
import org.mapstruct.Mapper;

@Mapper
public interface AnnouncementMapper {
    AnnouncementDto announcementToAnnouncementDto(Announcement announcement);
}
