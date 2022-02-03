package ar.edu.itba.paw.webapp.assembler;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.controller.AnnouncementsController;
import ar.edu.itba.paw.webapp.dto.AnnouncementDto;
import ar.edu.itba.paw.webapp.dto.CourseDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.mapper.AnnouncementMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementAssembler extends JaxRsResourceAssemblerSupport<Announcement, AnnouncementDto> {

    @Autowired
    private CourseAssembler courseAssembler;

    @Autowired
    private UserAssembler userAssembler;

    private final AnnouncementMapper mapper = Mappers.getMapper(AnnouncementMapper.class);

    public AnnouncementAssembler() {
        super(AnnouncementsController.class, AnnouncementDto.class);
    }

    @Override
    public AnnouncementDto toResource(Announcement entity) {
        AnnouncementDto announcement = createResourceWithId(entity.getAnnouncementId(), entity);
        AnnouncementDto result = mapper.announcementToAnnouncementDto(entity);
        UserDto author = userAssembler.toResource(entity.getAuthor());
        CourseDto course = courseAssembler.toResource(entity.getCourse());
        result.setAuthor(author);
        result.setCourse(course);
        result.add(announcement.getLinks());
        return result;
    }
}
