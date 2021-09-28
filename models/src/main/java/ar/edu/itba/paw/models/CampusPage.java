package ar.edu.itba.paw.models;

import java.util.Collections;
import java.util.List;

public class CampusPage<T> {
    List<T> content;
    Integer size;
    Integer total;
    Integer page;

    public CampusPage() {
        this.content = Collections.emptyList();
        this.size = 0;
        this.total = 1;
        this.page = 1;
    }

    public CampusPage(List<T> content, Integer size, Integer page, Integer total) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    public List<T> getContent() {
        return content;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }
}
