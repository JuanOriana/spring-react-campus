package ar.edu.itba.paw.webapp.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class AppPageRequest extends PageRequest implements Pageable {
    private final Integer page;
    private final Integer size;
    private AppPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
        this.page = page;
        this.size = size;
    }

    public static AppPageRequest of(int page, int size) {
        return of(page, size, Sort.unsorted());
    }

    public static AppPageRequest of(int page, int size, Sort sort) {
        return new AppPageRequest(page, size, sort);
    }

    @Override
    public long getOffset() {
        return (long) (page - 1) * size;
    }
}
