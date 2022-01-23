package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.CampusPage;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Component
public class ResponsePaging<T> {


    public Response.ResponseBuilder paging(CampusPage<T> campusPage, Response.ResponseBuilder response, UriInfo uriInfo,Integer pageSize){
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).build().toString(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", campusPage.getTotal()).queryParam("pageSize", pageSize).build().toString(), "last");

        if(!campusPage.getPage().equals(campusPage.getTotal())){
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page",campusPage.getPage() + 1).queryParam("pageSize", pageSize).build().toString(), "next");

        }

        if(campusPage.getPage() > 1) {
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", campusPage.getPage() - 1).queryParam("pageSize", pageSize).build().toString(), "prev");
        }

        return response;
    }
}
