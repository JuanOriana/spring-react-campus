package ar.edu.itba.paw.webapp.common.assemblers;

import ar.edu.itba.paw.models.Announcement;
import ar.edu.itba.paw.webapp.controllers.AnnouncementsController;
import ar.edu.itba.paw.webapp.dto.announcement.AnnouncementDto;
import ar.edu.itba.paw.webapp.dto.course.CourseDto;
import ar.edu.itba.paw.webapp.dto.user.UserDto;
import ar.edu.itba.paw.webapp.common.mappers.AnnouncementMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnnouncementAssembler{

    @Autowired
    private CourseAssembler courseAssembler;

    @Autowired
    private UserAssembler userAssembler;

    private final AnnouncementMapper mapper = Mappers.getMapper(AnnouncementMapper.class);

    public AnnouncementAssembler() {
        // For spring
    }

    public AnnouncementDto toResource(Announcement entity, boolean showDeepLinks) {
        AnnouncementDto result = mapper.announcementToAnnouncementDto(entity);
        UserDto author = userAssembler.toResource(entity.getAuthor(), showDeepLinks);
        CourseDto course = courseAssembler.toResource(entity.getCourse(), showDeepLinks);
        result.setAuthor(author);
        result.setCourse(course);
        List<Link> links = new ArrayList<>();
        links.add(JaxRsLinkBuilder.linkTo(AnnouncementsController.class).slash(entity.getAnnouncementId()).withSelfRel());
        result.setLinks(links);
        return result;
    }

    public List<AnnouncementDto> toResources(List<Announcement> announcements, boolean showDeepLinks) {
        List<AnnouncementDto> announcementDtoList = new ArrayList<>();
        announcements.forEach(a -> announcementDtoList.add(toResource(a, showDeepLinks)));
        return announcementDtoList;
    }
}
