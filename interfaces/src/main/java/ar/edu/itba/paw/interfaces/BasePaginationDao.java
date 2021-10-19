package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.CampusPageRequest;

import java.util.Map;

public interface BasePaginationDao<T> {
    CampusPage<T> listBy(Map<String, Object> properties, String query, String mappingQuery, CampusPageRequest pageRequest,
                         Class<T> target);
    int getTotalPageCount(String query, Map<String, Object> properties, Integer pageSize);

}
