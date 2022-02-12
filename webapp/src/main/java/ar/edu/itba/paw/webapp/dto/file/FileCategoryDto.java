package ar.edu.itba.paw.webapp.dto.file;

import org.springframework.hateoas.Link;
import java.io.Serializable;
import java.util.List;

public class FileCategoryDto implements Serializable {

    private long categoryId;
    private String categoryName;
    private List<Link> links;

    public FileCategoryDto() {
        // For MessageBodyWriter
    }


    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
