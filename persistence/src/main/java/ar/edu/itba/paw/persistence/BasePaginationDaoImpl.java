package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.BasePaginationDao;
import ar.edu.itba.paw.models.CampusPage;
import ar.edu.itba.paw.models.CampusPageRequest;
import ar.edu.itba.paw.models.exception.PaginationArgumentException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BasePaginationDaoImpl<T> implements BasePaginationDao<T> {

    @PersistenceContext
    EntityManager em;

    private List<Long> integerToLongArray(List<Integer> list) {
        return list.stream()
                .mapToLong(Integer::longValue)
                .boxed().collect(Collectors.toList());
    }

    @Override
    public CampusPage<T> listBy(Map<String, Object> properties, String query, String mappingQuery, CampusPageRequest pageRequest,
                                Class<T> target) {

        int pageCount = getTotalPageCount(query, properties, pageRequest.getPageSize());
        if(pageRequest.getPage() > pageCount) throw new PaginationArgumentException();
        if(pageCount == 0) return new CampusPage<>();
        String idsQueryString = query + " LIMIT " + pageRequest.getPageSize() + " OFFSET "
                + (pageRequest.getPage() - 1) * pageRequest.getPageSize();
        Query idsQuery = em.createNativeQuery(idsQueryString);
        for(Map.Entry<String, Object> entry : properties.entrySet()) {
            idsQuery.setParameter(entry.getKey(), entry.getValue());
        }
        TypedQuery<T> dataQuery = em.createQuery(mappingQuery, target);
        dataQuery.setParameter("ids", integerToLongArray(idsQuery.getResultList()));
        return new CampusPage<>(dataQuery.getResultList(), pageRequest.getPageSize(), pageRequest.getPage(), pageCount);
    }

    @Override
    public int getTotalPageCount(String query, Map<String, Object> properties, Integer pageSize) {
        String rowCountSql = "SELECT count(1) AS row_count FROM (" + query + ") as foo";
        final Query dataCountQuery = em.createNativeQuery(rowCountSql);
        for(Map.Entry<String, Object> entry : properties.entrySet()) {
            dataCountQuery.setParameter(entry.getKey(), entry.getValue());
        }
        Number count = (Number) dataCountQuery.getSingleResult();
        return (int) Math.ceil(count.doubleValue() / pageSize);
    }
}
