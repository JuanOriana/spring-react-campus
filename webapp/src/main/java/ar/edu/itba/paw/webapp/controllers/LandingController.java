package ar.edu.itba.paw.webapp.controllers;

import org.springframework.hateoas.*;
import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("/api")
@Component
public class LandingController {

    @GET
    @Produces("application/vnd.campus.api.v1+json")
    public Response getHypermediaLinks() {
        ResourceSupport resource = new ResourceSupport();
        resource.add(JaxRsLinkBuilder.linkTo(LandingController.class).slash("user").withRel("user"));
        Link fileLink = new Link(
                new UriTemplate(
                        JaxRsLinkBuilder.linkTo(LandingController.class).slash("files").toString(),
                        new TemplateVariables(new TemplateVariable("category-type,extension-type,query,order-property,order-direction,page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                ), "files"
        );
        resource.add(fileLink);
        Link announcementsLink = new Link(
                new UriTemplate(
                        JaxRsLinkBuilder.linkTo(LandingController.class).slash("announcements").toString(),
                        new TemplateVariables(new TemplateVariable("page,page-size", TemplateVariable.VariableType.REQUEST_PARAM))
                ), "announcements"
        );
        resource.add(announcementsLink);
        return Response.ok(new GenericEntity<ResourceSupport>(resource){}).build();
    }
}
