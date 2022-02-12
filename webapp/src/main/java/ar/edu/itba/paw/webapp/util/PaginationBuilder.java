package ar.edu.itba.paw.webapp.util;

import ar.edu.itba.paw.models.CampusPage;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class PaginationBuilder {
    private PaginationBuilder() {
        // Avoid instantiation of util class
    }

    public static <T> Response build(CampusPage<T> content,
                                     Response.ResponseBuilder builder,
                                     UriInfo uriInfo,
                                     Integer pageSize) {
        builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).queryParam("pageSize", pageSize).build().toString(), "first")
                .link(uriInfo.getAbsolutePathBuilder().queryParam("page", content.getTotal()).queryParam("pageSize", pageSize).build().toString(), "last");

        if(!content.getPage().equals(content.getTotal())){
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page",content.getPage() + 1).queryParam("pageSize", pageSize).build().toString(), "next");

        }

        if(content.getPage() > 1) {
            builder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", content.getPage() - 1).queryParam("pageSize", pageSize).build().toString(), "prev");
        }

        builder.header("X-Total-Pages", content.getTotal());
        return builder.build();
    }
}
