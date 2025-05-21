package ru.hogwarts.school.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;

@Service
public class ThreadValidateUnique {


    @PersistenceContext
    private EntityManager entityManager;

    public <T> Long validateUniqueField(Class<T> entityClass, String fieldName, Object value) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(entityClass);
        query.select(cb.count(root))
                .where(cb.equal(root.get(fieldName), value));
        return entityManager.createQuery(query).getSingleResult();
    }

}
