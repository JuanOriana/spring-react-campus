package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import org.mapstruct.Mapper;

@Mapper
public interface AnnouncementMapper {
    AnnouncementDto announcementToAnnouncementDto(Announcement announcement);
}
