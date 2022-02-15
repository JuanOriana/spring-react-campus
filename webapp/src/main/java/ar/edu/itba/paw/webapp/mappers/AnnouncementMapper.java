package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.dtos.announcement.AnnouncementDto;
import org.mapstruct.Mapper;

@Mapper
public interface AnnouncementMapper {
    AnnouncementDto announcementToAnnouncementDto(Announcement announcement);
}
